# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
resource "aws_iam_role" "chatbot_role" {
  name               = var.role_name
  tags               = merge(var.tags, var.iam_role_tags)
  assume_role_policy = var.assume_role_policy
}

resource "aws_iam_role_policy_attachment" "this" {
  role       = var.role_name
  policy_arn = var.policy_arn
  depends_on = [aws_iam_role.chatbot_role]
}