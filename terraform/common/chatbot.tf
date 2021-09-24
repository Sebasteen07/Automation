# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

data "aws_iam_policy_document" "chatbot_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["chatbot.amazonaws.com"]
    }
  }
}

resource "aws_iam_policy" "chatbot_sns_permissions" {
  name        = "pxp-mf-qa-chatbot-role-policy"
  description = "Chatbot Role to subscribe to SNS topic"

  policy      = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": [
              "sns:ListSubscriptionsByTopic",
              "sns:ListTopics",
              "sns:Unsubscribe",
              "sns:Subscribe",
              "sns:ListSubscriptions"
            ],
            "Effect": "Allow",
            "Resource": "${module.sns_module.sns_topic.arn}"
        },
        {
            "Effect": "Allow",
            "Action": [
              "logs:PutLogEvents",
              "logs:CreateLogStream",
              "logs:DescribeLogStreams",
              "logs:CreateLogGroup",
              "logs:DescribeLogGroups"
            ],
            "Resource": "arn:aws:logs:*:*:log-group:/aws/chatbot/*"
        },
        {
            "Effect": "Allow",
            "Action": [
              "kms:Encrypt",
              "kms:Decrypt",
              "kms:ReEncrypt*",
              "kms:GenerateDataKey*",
              "kms:DescribeKey",
              "kms:CreateGrant",
              "kms:ListGrants",
              "kms:RevokeGrant"
            ],
            "Resource": "${aws_ssm_parameter.kms_key_arn.value}"
        }
    ]
}
EOF
}

module "chatbot_module" {
  source                  = "./modules/chatbot"

  iam_role_tags           = { "Name" = "pxp-mf-qa-${var.environment}-${local.chatbot_role_name}" }
  role_name               = "pxp-mf-qa-${var.environment}-${local.chatbot_role_name}"
  policy_arn              = aws_iam_policy.chatbot_sns_permissions.arn
  assume_role_policy      = data.aws_iam_policy_document.chatbot_assume_role_policy.json
  cloudform_name          = "pxp-mf-qa-${var.environment}-${local.cfn_stack_name}"
  sns_topic_arn           = module.sns_module.sns_topic.arn
  config_name             = "pxp-mf-qa-${var.environment}-${local.config_name}"
  slack_channel_id        = var.slack_channel_id
  slack_channel_workspace = var.slack_channel_workspace
  logging_level           = var.logging_level
}


