# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

resource "aws_ssm_parameter" "kms_key_arn" {
  name        = local.encryption_key_parameter_name
  description = local.encryption_key_parameter_description
  type        = "String"
  key_id      = local.kms_key_arn
  value       = local.kms_key_arn
}

resource "aws_ssm_parameter" "codepipeline_artifact_bucket_name" {
  name        = local.artifact_store_parameter_name
  description = local.artifact_store_parameter_description
  type        = "String"
  key_id      = aws_ssm_parameter.kms_key_arn.value
  value       = local.codepipeline_artifact_bucket_name
}
