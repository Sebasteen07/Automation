# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

data "aws_iam_account_alias" "current" {}

resource "aws_sns_topic" "this" {
  name         = var.topic_name
  display_name = var.display_name

  kms_master_key_id = var.kms_master_key_id

  http_failure_feedback_role_arn    = var.http_failure_feedback_role_arn
  http_success_feedback_role_arn    = var.http_success_feedback_role_arn
  http_success_feedback_sample_rate = var.http_success_feedback_sample_rate

  delivery_policy = <<EOF
{
  "http": {
    "defaultHealthyRetryPolicy": {
      "minDelayTarget": ${var.minDelayTarget},
      "maxDelayTarget":  ${var.maxDelayTarget},
      "numRetries": ${var.numRetries},
      "numMaxDelayRetries": ${var.numMaxDelayRetries},
      "numNoDelayRetries": ${var.numNoDelayRetries},
      "numMinDelayRetries": ${var.numMinDelayRetries},
      "backoffFunction": ${var.backoffFunction}
    },
    "disableSubscriptionOverrides": ${var.disableSubscriptionOverrides},
    "defaultThrottlePolicy": {
      "maxReceivesPerSecond": ${var.maxReceivesPerSecond}
    }
  }
}
EOF
  tags            = var.tags
}

resource "aws_sns_topic_policy" "default" {
  arn = aws_sns_topic.this.arn

  policy = data.aws_iam_policy_document.default.json
}

data "aws_iam_policy_document" "default" {
  policy_id = "${var.topic_name}-default-policy-id"

  statement {
    actions = [
      "sns:Subscribe",
      "sns:SetTopicAttributes",
      "sns:RemovePermission",
      "sns:Receive",
      "sns:Publish",
      "sns:ListSubscriptionsByTopic",
      "sns:GetTopicAttributes",
      "sns:DeleteTopic",
      "sns:AddPermission",
    ]

    condition {
      test     = "StringEquals"
      variable = "AWS:SourceOwner"

      values = [
        data.aws_iam_account_alias.current.id,
      ]
    }

    effect = "Allow"

    principals {
      type        = "AWS"
      identifiers = ["*"]
    }

    resources = [
      aws_sns_topic.this.arn,
    ]

    sid = "${var.topic_name}-default-statement-ID"
  }

  dynamic "statement" {
    for_each = var.additional_statements
    content {
      sid     = statement.value["sid"]
      actions = statement.value["actions"]
      effect  = statement.value["effect"]
      resources = [
        aws_sns_topic.this.arn,
      ]

      dynamic "condition" {
        for_each = statement.value["conditions"]
        content {
          test     = condition.value["test"]
          variable = condition.value["variable"]
          values   = condition.value["values"]
        }
      }

      principals {
        type        = statement.value["principals"]["principal_type"]
        identifiers = statement.value["principals"]["identifiers"]
      }
    }
  }

}
