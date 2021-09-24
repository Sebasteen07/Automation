# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

data "aws_iam_policy_document" "sns_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["sns.amazonaws.com"]
    }
  }
}

module "sns_module" {
  source = "./modules/notification-topic"

  topic_name   = "pxp-mf-qa-${var.environment}-chatbot-sns-topic"
  display_name = "PxP MF QA ${var.environment} chatbot SNS topic"

  kms_key_id = aws_ssm_parameter.kms_key_arn.value

  http_failure_feedback_role_arn    = aws_iam_role.sns_delivery_status_failure.arn
  http_success_feedback_role_arn    = aws_iam_role.sns_delivery_status_success.arn
  http_success_feedback_sample_rate = 100

  additional_statements = [{
    sid = "allow-publish-for-cloudwatch-events-statement"
    actions = [
      "sns:Publish"
    ]
    effect = "Allow"
    principals = {
      principal_type = "Service"
      identifiers    = ["events.amazonaws.com"]
    }
    conditions = []
  }]
}

# Roles for delivery status logging
# SUCCESS status delivery role:
resource "aws_iam_role" "sns_delivery_status_success" {
  name                = "pxp-mf-qa-${var.environment}-chatbot-sns-topic-SUCCESS-role"
  assume_role_policy  = data.aws_iam_policy_document.sns_assume_role_policy.json
  managed_policy_arns = [aws_iam_policy.success.arn]
}

resource "aws_iam_policy" "success" {
  name = "pxp-mf-qa-${var.environment}-chatbot-sns-topic-SUCCESS-policy"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents",
          "logs:PutMetricFilter",
          "logs:PutRetentionPolicy"
        ]
        Effect   = "Allow"
        Resource = "*"
      },
    ]
  })
}

# FAILURE status delivery role:
resource "aws_iam_role" "sns_delivery_status_failure" {
  name                = "pxp-mf-qa-${var.environment}-chatbot-sns-topic-failure-role"
  assume_role_policy  = data.aws_iam_policy_document.sns_assume_role_policy.json
  managed_policy_arns = [aws_iam_policy.failure.arn]
}

resource "aws_iam_policy" "failure" {
  name = "pxp-mf-qa-${var.environment}-chatbot-sns-topic-FAILURE-policy"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents",
          "logs:PutMetricFilter",
          "logs:PutRetentionPolicy"
        ]
        Effect   = "Allow"
        Resource = "*"
      },
    ]
  })
}