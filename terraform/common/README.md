# PxP QA CodePipeline - common resources
This directory contains Terraform configurations to create a common AWS resources for PxP QA CodePipeline.

## Pre-requisites
- None

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

## Inputs
| Name | Description | Type | Default | Required |
|---|---|---|---|:--------:|
| region | AWS region where the resurces would be created | `string` | us-east-2 | no |
| role_arn | ARN of the IAM role that Terraform would assume in order to create the resources | `string` | arn:aws:iam::997401518295:role/NextGenAdminAccess | no |
| base_tags | Map of tags added to all AWS resources | `map` | <pre>{<br>  "nextgen.automation"            = "true"<br>  <br>  "nextgen.component"            = "PXP"<br>  "nextgen.data-classification"   = "sensitive"}</pre> | no |

## Outputs
| Name | Description |
|---|---|
|  |  |

## Deployment diagram

