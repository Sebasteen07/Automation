# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
resource "aws_cloudwatch_event_rule" "trigger_codepipeline" {
  name        = "${local.name}-trigger"
  description = "Trigger pipeline execution when changes are pushed to master branch in CodeCommit."

  schedule_expression = try(local.inputs[terraform.workspace].cron_shedule)
}

resource "aws_cloudwatch_event_target" "trigger_codepipeline" {
  rule      = aws_cloudwatch_event_rule.trigger_codepipeline.name
  target_id = "pipeline"
  role_arn  = aws_iam_role.cwevent.arn
  arn       = aws_codepipeline.main.arn
}

data "aws_iam_policy_document" "cwevent_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type = "Service"

      identifiers = ["events.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "cwevent" {
  name               = "${local.name}-cwevent"
  assume_role_policy = data.aws_iam_policy_document.cwevent_assume_role_policy.json
}

data "aws_iam_policy_document" "cwevent_codepipeline" {
  statement {
    actions = [
      "codepipeline:StartPipelineExecution",
    ]

    resources = [
      aws_codepipeline.main.arn,
    ]
  }
}

resource "aws_iam_role_policy" "cw_events_policy" {
  name   = "${local.name}-cwevents-policy"
  role   = aws_iam_role.cwevent.name
  policy = data.aws_iam_policy_document.cwevent_codepipeline.json
}
