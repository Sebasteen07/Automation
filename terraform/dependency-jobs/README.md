# PxP QA CodePipeline - jobs with dependencies

## Overview
This directory contains Terraform configurations to create a dependency-jobs via AWS CodePipeline for PxP QA CodePipeline.

## Pre-requisites
The following resources must be created using the `common` folder
  - CodeCommit repository `qa-automation-mirror` for mirroring the qa-automation repo from Bitbucket
  - KMS keys ARN stored in SSM parameter - /nextgen/kms/data/default for data encryption key
  - Bucket ARN stored in SSM parameter - /nextgen/codepipeline/artifact-store/default for storing CodePipeline artifacts & logs
  - SNS topic ARN for Slack notifications

## AWS Design Reference
![AWS_Design](assets/pxp-qa-automation-design.png)

## Terraform versions
### Recommended
- Terraform v 1.0.0 and above  
- AWS Provider v 3.59.0 and above

## Inputs

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|:--------:|
| codebuild\_artifacts\_type | The build output artifact's type, default to CODEPIPELINE | `string` | `"CODEPIPELINE"` | no |
| codebuild\_compute\_type | Information about the compute resources the build project will use | `string` | `"BUILD_GENERAL1_SMALL"` | no |
| codebuild\_image | Container image to be used in AWS CodeBuild for the CI | `string` | `"aws/codebuild/amazonlinux2-x86_64-standard:3.0"` | no |
| codebuild\_image\_pull\_credentials\_type | The type of credentials AWS CodeBuild uses to pull images in your build. Available values for this parameter are CODEBUILD or SERVICE\_ROLE, default to CODEBUILD | `string` | `"CODEBUILD"` | no |
| codebuild\_privileged\_override | If set to true, enables running the Docker daemon inside a Docker container. Defaults to false. | `bool` | `true` | no |
| codebuild\_source\_type | The type of repository that contains the source code to be built. default to CODEPIPELINE | `string` | `"CODEPIPELINE"` | no |
| codebuild\_type | The type of build environment to use for related builds, default to LINUX\_CONTAINER | `string` | `"LINUX_CONTAINER"` | no |
| component | Component defination | `string` | `"PXP"` | no |
| data\_classification | Data classification for the CI/CD pipeline | `string` | `"confidential"` | no |
| environment | The name of environment. Used to differentiate costs between stacks or pipelines including those of the same type | `string` | `"dev"` | no |
| environment-type | The type of environment according to function/purpose. Used to differentiate costs between different types of use | `string` | `"test"` | no |
| repository\_name | The name of the repository that would be used to trigger the pipeline | `string` | `"qa-automation-mirror"` | no |
| source\_buildspec | Location of the buildspec file in the nextgen-portal-api-gateway repository | `string` | `"terraform/testing-jobs/buildspecfolder/buildspec.yaml"` | no |

## Outputs

| Name | Description |
|------|-------------|
| codebuild\_project\_role | The codebuild project role |

## Locals
The following locals are configured as an Inputs to the testing-job terraform code, if required can be modified.

```
vpc_name                      = "main"
kms_key_id                    = data.aws_ssm_parameter.kms_data_default.value
aws_codebuild_project_arn     = "arn:aws:codebuild:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:project/${module.qa_automation_utils_rel_codebuild.codebuild_project.name}"
pipeline_artifact_bucket_name = data.aws_ssm_parameter.codepipeline_artifact_store.value
pipeline_artifact_bucket_arn  = "arn:aws:s3:::${local.pipeline_artifact_bucket_name}"
name                          = terraform.workspace
aws_codecommit_repo_arn       = "arn:aws:codecommit:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:${var.repository_name}"
selenium_browser              = "*chrome"
test_execution_mode           = "headless"
common_tags = {
  "nextgen.automation"          = true
  "nextgen.component"           = var.component
  "nextgen.data-classification" = var.data_classification
  "nextgen.environment"         = var.environment
  "nextgen.environment-type"    = var.environment-type
}
````

The following locals are configured as an Inputs mapping to each spcific testing-job terraform code, which needs modification.

```
inputs = {
  "default" = {
    codecommit_branch     = "master"
    PollForSourceChanges  = true
    execution_folder      = "pi-integration-platform-acceptance"
    suite_xml             = "codebuild.xml"
    build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
    queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
    maven_parameter       = "mvn -U clean install"
    google_chrome_version = "93.0.4577.82-1"
    chrome_driver_version = "92.0.4515.107"
    cron_shedule          = "cron(0 10 * * ? *)"
  }
}
```

## Usage
This requires Terraform version 1.0.0 and above.

The repository is set up to substitute the current workspace name as the testing job purpose. So, make sure that the Terraform workspace by the testing job purpose is created and the selected workspace is the one whose name is the same as the defined testing job purpose.  
Example:
```
locals {
  valid_workspaces = {
    default = "default"
    piIntPlatformAcceptance-demo-codebuild = "piIntPlatformAcceptance-demo-codebuild"
  }
  selected_workspace = local.valid_workspaces[terraform.workspace]
}
```

Copy the <default> section from the locals.tf file (also copied in the above section) & paste it by changing it to supported <testing job purpose> as described in the workspace.tf also in above section. Fill in all the required env specific information for the new testing job.

### Switching terraform workspace
For example, if the name of the testing job purpose is 'piIntPlatformAcceptance-demo-codebuild' & the workspace is not exits, then create a workspace named `piIntPlatformAcceptance-demo-codebuild`
```
terraform workspace new piIntPlatformAcceptance-demo-codebuild
```
List the current available workspace & identify the selected workspace by symbol '*' in front of workspace name
```
terraform workspace list
```
Select 'piIntPlatformAcceptance-demo-codebuild' as the current workspace, if already
```
terraform workspace select piIntPlatformAcceptance-demo-codebuild
```

Additionally, some parameter defaults are set specifically set for specific testing job purpose/regions(for pxp-build, strictly us-east-2).
Go through the locals section above to understand the various local reference for execution and update accordingly.  

Using the configurations is easy. Run the following commands from within the `terraform/testing-jobs/` directory:
```
terraform init
```
Create a plan
```
terraform plan -no-color -out=pipeline.tfplan
```
Review the generated plan and once satisfied execute it using the `terraform apply` command
```
terraform apply pipeline.tfplan
```
