# PxP QA CodePipeline - common resources
This directory contains Terraform configurations to create a common AWS resources for PxP QA CodePipeline.

## Pre-requisites
- IAM user that can be used to mirror the CodeCommit repository must be created by the AD team
- The same IAM user must be provided for the variable `codecommit_user`

## Terraform versions
### Recommended
- Terraform v 1.0.0 and above  
- AWS Provider v 3.59.0 and above

## Resources
The following resources are created by this configuration
1. CodeCommit repository by the name `qa-automation-mirror` for mirroring the `qa-automation` repo from Bitbucket
1. KMS keys
  - `/nextgen/kms/data/default` for storing the ARN of account's data encryption key
  - `/nextgen/codepipeline/artifact-store/default` for storing the name of artifact bucket for CodePipeline
1. SNS topic for Slack notifications
1. AWS Chatbot for Slack notifications
1. IAM roles and policies required for SNS and Chatbot
1. IAM Group and group policy allowing all operations for the CodeCommit repository created

## Inputs
| Name | Description | Type | Default | Required |
|---|---|---|---|:--------:|
| region | AWS region where the resurces would be created | `string` | "us-east-2" | no |
| environment | Environment name | `string` | "dev" | no |
| environment_type | Type of environment | `string` | "test" | no |
| role_arn | ARN of the IAM role that Terraform would assume in order to create the resources | `string` | arn:aws:iam::997401518295:role/NextGenAdminAccess | no |
| codecommit_user | IAM user used to mirror the Bitbucket repositories to CodeCommit repositories - this must be created by the AD team before running these configurations | `string` | "codecommit_mirror" | no |
| slack_channel_id | The slack channel id to send notify | `string` | "GR0N2AYV6" | no |
| slack_channel_workspace | The slack channel workspace id | `string` | "T0VUPL2KS" | no |
| logging_level | The configured slack channel logging level | `string` | "INFO" | no |

## Outputs
| Name | Description |
|---|---|
| qa_automation_codecommit_repo_name | AWS CodeCommit repo to which the Bitbucket repo qa-automation would mirror its content |
| codepipeline_artifact_bucket_parameter_name | S3 bucket name used for the CodePipeline jobs |
| data_encryption_key_parameter_name | SSM parameter name for storing the value of data encryption KMS key for this account |
| sns_topic_arn | The SNS topic created by this module for sending notifications to AWS Chatbot |
| chatbot_iam_role_arn | IAM role assumed by the AWS Chatbot channel |
|  |  |
|  |  |
|  |  |
|  |  |

## Deployment diagram

