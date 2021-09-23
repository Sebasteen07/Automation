# SNS topic
This directory contains Terraform configurations to create an SNS topic.

## Pre-requisites
- None

## Terraform versions
### Recommended 
- Terraform v 0.14.0 and above  
- AWS Provider v 3.27.0 and above

### Minimum 
- Terraform v 0.12.0 and above
- AWS Provider v 2.67.0 and above

## Resources
This terraform configuration creates the following resources on AWS:
- Amazon SNS topic
- SNS topic policy

## Inputs
| Name | Description | Type | Default | Required |
|---|---|---|---|:--------:|
| topic_name | The friendly name for the SNS topic | string | - | yes |
| display_name | The display name for the SNS topic | string | - | yes |
| kms_master_key_id | The ID of an AWS-managed customer master key (CMK) for Amazon SNS or a custom CMK (Recommended to use InfoSec provided key) | string | "alias/aws/sns" | no |
| minDelayTarget | Minimum Delay Target | number | 20 | no |
| maxDelayTarget | Maximum Delay Target | number | 20 | no |
| numRetries | Number of retries | number | 3 | no |
| numMaxDelayRetries | Max number of delay retries | number | 0 | no |
| numNoDelayRetries | Number of no-delay retries | number | 0 | no |
| numMinDelayRetries | Number of minimum delay retries | number | 0 | no |
| backoffFunction | Back-off Function | string | "linear" | no |
| disableSubscriptionOverrides | Disable Subscription Overrides? | boolean | false | no |
| maxReceivesPerSecond | Maximum receives per second | number | 1 | no |
| additional_statements | Additional IAM policy statement values as a map of list items. Should be passed as [] if no additional policy is required | list(objects) | [] | no |
| tags | Key-value map of resource tags | map | - | yes |
## Outputs
| Name | Description |
|---|---|
| sns_topic | This SNS topic |


## Release Notes
### Ver 1.0 June 2021
This is the initial version