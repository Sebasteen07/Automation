# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

resource "aws_cloudformation_stack" "cloudformation_aws_chatbot" {
  name = var.cloudform_name

  parameters = {
    configName            = var.config_name
    slackChannelId        = var.slack_channel_id
    slackChannelWorkspace = var.slack_channel_workspace
    loggingLevel          = var.logging_level
    iamRoleArn            = aws_iam_role.chatbot_role.arn
    snsTopicArn           = var.sns_topic_arn
  }

  template_body = file("${path.module}/files/chatbot.yml")
}