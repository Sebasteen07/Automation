# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
output "iam_role" {
  description = "The iam role to be assumed by Chatbot"
  value       = aws_iam_role.chatbot_role
}
