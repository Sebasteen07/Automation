# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

data "aws_iam_user" "codecommit_user" {
  user_name = var.codecommit_user
}