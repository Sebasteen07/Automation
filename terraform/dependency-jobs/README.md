# PxP QA CodePipeline - jobs with dependencies

## Overview
This directory contains Terraform configurations to create a dependency-jobs via AWS CodePipeline for PxP QA CodePipeline.

## Pre-requisites
The following resources must be created using the `common` folder
  - CodeCommit repository `qa-automation-mirror` for mirroring the [qa-automation](https://bitbucket.nextgen.com/projects/MFQA/repos/qa-automation/browse) repo from Bitbucket
  - KMS keys ARN stored in SSM parameter - `/nextgen/kms/data/default` for data encryption key
  - Bucket ARN stored in SSM parameter - `/nextgen/codepipeline/artifact-store/default` for storing CodePipeline artifacts & logs
  - AWS Chatbot channel for Slack notifications

## Terraform versions
### Recommended
- Terraform v 1.0.0 and above  
- AWS Provider v 3.59.0 and above

## Inputs

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|:--------:|
| region | AWS region where the resurces would be created | string | "us-east-2" | no |
| role_arn | ARN of the IAM role that Terraform would assume in order to create the resources | string | arn:aws:iam::997401518295:role/NextGenAdminAccess | no |
| environment | The name of environment. Used to differentiate costs between stacks or pipelines including those of the same type | string | "dev" | no |
| environment-type | The type of environment according to function/purpose. Used to differentiate costs between different types of use | string | "test" | no |
| component | Component definition | string | "PXP" | no |
| data_classification | Data classification for the CI/CD pipeline | string | "confidential" | no |
| repository_name | The name of the repository that would be used to trigger the pipeline | string | "qa-automation-mirror" | no |
| source_buildspec | Location of the buildspec file in the nextgen-portal-api-gateway repository | string | "terraform/testing-jobs/buildspecfolder/buildspec.yaml" | no |
| codebuild_image | Container image to be used in AWS CodeBuild for the CI | string | "aws/codebuild/amazonlinux2-x86_64-standard:3.0" | no |
| codebuild_artifacts_type | The build output artifact's type, default to CODEPIPELINE | string | "CODEPIPELINE" | no |
| codebuild_source_type | The type of repository that contains the source code to be built. | string | "CODEPIPELINE" | no |
| codebuild_compute_type | Information about the compute resources the build project will use | string | "BUILD_GENERAL1_SMALL" | no |
| codebuild_privileged_override | If set to true, enables running the Docker daemon inside a Docker container. Dfaults to false. | bool | `true` | no |
| codebuild_type | The type of build environment to use for related builds, default to LINUX_CONTAINER | string | "LINUX_CONTAINER" | no |
| codebuild_image_pull_credentials_type | The type of credentials AWS CodeBuild uses to pull images in your build. Available values for this parameter are CODEBUILD or SERVICE_ROLE, default to CODEBUILD | string | "CODEBUILD" | no |
| notification_detail_type | Level of detail that would be sent to the Slack channel for the notification (BASIC or FUll) | string | "BASIC" | no |
| event_type_ids | Pipeline event types that would trigger a notification to be sent to the Slack channel | list | [\n    "codepipeline-pipeline-pipeline-execution-succeeded",\n    "codepipeline-pipeline-pipeline-execution-failed",\n    "codepipeline-pipeline-pipeline-execution-canceled"\n
  ] | no |
| codeartifact_maven_domain | Domain name of the AWS CodeArtifact repository where the Maven build packages would be uploaded | string | "nextgen-pxp-mf-build" | no |
| codeartifact_maven_repo | AWS CodeArtifact repository where the Maven build packages would be uploaded | string | "pxp-mf" | no |
| aws_chatbot_channel_arn | ARN of the AWS Chatbot channel with which the Slack channel is associated | string | "arn:aws:chatbot::997401518295:chat-configuration/slack-channel/pxp-mf-qa-dev-alarms-notify-slack-channel" | no |


## Outputs

| Name | Description |
|------|-------------|
| qa_automation_utils_codebuild_project_role | The codebuild project role for git-maven-build-qa-automation-utils-rel |
| qa_automation_utils_codepipeline_name | The codepipeline id created for test execution of git-maven-build-qa-automation-utils-rel |
| qa_automation_utils_cloudwatch_event_rule_name | Cloudwatch event rule name created for triggering pipeline execution of git-maven-build-qa-automation-utils-rel |

## Locals
The following locals are configured as an Inputs to the testing-job terraform code, if required can be modified.

```
vpc_name                      = "main"
kms_key_id                    = data.aws_ssm_parameter.kms_data_default.value
pipeline_artifact_bucket_name = data.aws_ssm_parameter.codepipeline_artifact_store.value
pipeline_artifact_bucket_arn  = "arn:aws:s3:::${local.pipeline_artifact_bucket_name}"
````

The following locals are configured as an Inputs mapping to each specific dependency-job terraform code, which needs modification.

For example, the job git-maven-build-qa-automation-utils-rel implemented by `git-maven-build-qa-automation-utils-rel.tf` has the following inputs that are defined in the `locals.tf`:

```
qa_automation_utils = {
    name                  = "git-maven-build-qa-automation-utils-rel"
    codecommit_branch     = "development"
    PollForSourceChanges  = true
    execution_folder      = "qa-automation-utils"
    maven_test_skip       = "true"
    build_timeout         = 240
    queued_timeout        = 480
    maven_parameter       = "mvn clean install"
}
```
When creating a new dependency job:
1. Add new job's corresponding inputs in the `local.tf` as in the above example for `qa_automation_utils`
1. Copy the `example.tf` from the `example-job` directory and rename it with the actual job name.
1. Open the renamed tf file and search all occurences of `dependency_jobname` and replace it with the actual job's name (with `_` instead of `-`). For example, in `git-maven-build-qa-automation-utils-rel.tf`, all occurences of `dependency_jobname` were replaced with `qa_automation_utils`
1. Create a plan and apply
1. Test as per requirements and make necessary changes (if needed)

## Usage
This requires Terraform version 1.0.0 and above.

Go through the locals section above to understand the various local reference for execution and update accordingly.  

For using the configurations run the following commands from within the `terraform/testing-jobs/` directory:
```
terraform init
```
Create a plan
```
terraform plan -no-color -out=terraform.plan | tee terraform.plan.log
```
Review the generated plan `terraform.plan.log` and once satisfied that it is creating/modifying only intended resources, execute it using the `terraform apply` command
```
terraform apply terraform.plan
```
