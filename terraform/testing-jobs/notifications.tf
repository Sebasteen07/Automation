#Copyright 2021 NXGN Management, LLC. All Rights Reserved.
resource "aws_codestarnotifications_notification_rule" "codepipeline_notification" {
  provider = aws.pipeline

  detail_type = "BASIC" #BASIC or FUll
  event_type_ids = [
    "codepipeline-pipeline-pipeline-execution-succeeded",
    "codepipeline-pipeline-pipeline-execution-failed",
    "codepipeline-pipeline-pipeline-execution-canceled"
  ]

  name     = "${local.name}-notification"
  resource = aws_codepipeline.main.arn

  target {
    address = data.aws_sns_topic.slack.arn
  }
}
