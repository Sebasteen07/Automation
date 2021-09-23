# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

resource "aws_codecommit_repository" "qa_automation_repo" {
  repository_name = local.repository_name["qa-automation"]
  description     = local.repository_desc["qa-automation"]

  tags = local.environment_tags
}

resource "aws_iam_group" "qa_automation" {
  name = "${local.repository_name["qa-automation"]}-qa-automation-group"
  path = "/"
}

resource "aws_iam_group_policy" "qa_automation_policy" {
  name  = "${local.repository_name["qa-automation"]}-qa-automation-policy"
  group = aws_iam_group.qa_automation.id

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "codecommit:*"
      ],
      "Effect": "Allow",
      "Resource": "${aws_codecommit_repository.qa_automation_repo.arn}"
    }
  ]
}
EOF
}

resource "aws_iam_user_group_membership" "codecommit_user" {
  user = data.aws_iam_user.codecommit_user.user_name

  groups = [
    aws_iam_group.qa_automation.name,
  ]
}