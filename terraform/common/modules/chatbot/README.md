# AWS Chatbot
AWS chatbot that sends notifications to Slack.
-   Creates a AWS chatbot configured to a Slack channel using cloud formation

## Terraform versions
### Recommended
- Terraform v 1.0.0 and above  
- AWS Provider v 3.59.0 and above

## Inputs
| Name | Description  | Type| Default | Required |
|--|--|-- |--|--|
| region | The  name  of  the  aws  region  | string | us-east-2 | no |
| role_arn| The  arn  of  the  aws  role | string |arn:aws:iam::447374039575:role/NextGenAdminAccess | no |
| sns_topic_name | The  name  of  the  SNS  topic  to  create | string | prod-rm-notify-aws-chatbot | no |
| sns_tags | Tags for the SNS topic | string | Name = "prod-rm-notify-aws-chatbot" | no |
| sns_topic_display_name | The  name  of  the  SNS  topic  to  display | string | prod-rm-notify-aws-chatbot | no|
| kms_key_id | The  kms  master  key  id storted in parameter store | string | /nextgen/kms/organization/chatbot | no |
| min_delay_target | Minimum  delay  target  for  the  resource | number | 20 | no|
| max_delay_target | Maximum  delay  int  target  by  the  resource | number | 20 | no |
| num_retries | Number  of  retries  by  the  resource | number | 3 | no |
| num_max_delay_retries | Number  of  maximum  delay  in  retries  by  the  resource | number | 0 | no|
| num_no_delay_retries | Number  of  no  delay  in  retries  by  the  resource | number | 0 | no |
| backoff_function | Back  off  function | string | linear | no |
| disable_subscription_overrides | Disable  subscription  overrides | bool | false | no |
| max_receives_per_second | Maximum  number  of  receives  per  second  by  the  resource | number | 1 | no |
| role_name | Name  of  the  IAM  role  | string | "prod-rm-chatbot-monitoring-alarms-role" | no |
| iam_role_tags | Tags for iam role | string | Name = "prod-rm-chatbot-monitoring-alarms-role" | no |
| policy_arn | Arn of the policy attaching to role | map | NextGenReadOnlyAccess | no |
| cloudform_name | Name  of  the  cloudformation | string | prod-rm-awschatbot-notify-slack | no |
| config_name | The  slack  configuration  name | string | prod-rm-alarms-notify-slack-channel | no |
| slack_channel_id | The  slack  channel  id  to  send  notify | string | prod = "C0187A5CP1R" | no |
| slack_channel_workspace | The  slack  channel  workspace  id | string | T0VUPL2KS | no |
| logging_level | The  configured  slack  channel  logging  level | string | ERROR | no |

## Outputs
| Name | Description  |
|--|--|-- |--|--|
| chatbot_iam_role_arn | The ARN of the IAM role to be assumed by Chatbot |
| sns_topic_arn | The  ARN  of  the  SNS  topic  from  which  messages  will  be  sent  to  Chatbot |