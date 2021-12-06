# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
variable "component" {
  type        = string
  description = "Component defination"
  default     = "PXP"
}

variable "data_classification" {
  type        = string
  description = "Data classification for the CI/CD pipeline"
  default     = "confidential"
}

variable "environment" {
  type        = string
  default     = "build"
  description = "The name of environment. Used to differentiate costs between stacks or pipelines including those of the same type"
}

variable "environment-type" {
  type        = string
  default     = "test"
  description = "The type of environment according to function/purpose. Used to differentiate costs between different types of use"
}

variable "bitbucket_repository_name" {
  type        = string
  default     = "qa-automation"
  description = "Same as bitbucket repository name"
}

variable "repository_name" {
  type        = string
  description = "The name of the repository that would be used to trigger the pipeline"
  default     = "qa-automation-mirror"
}

variable "source_buildspec" {
  type        = string
  description = "Location of the buildspec file in the nextgen-portal-api-gateway repository"
  default     = "terraform/testing-jobs/buildspecfolder/buildspec.yaml"
}

variable "codebuild_image" {
  type        = string
  description = "Container image to be used in AWS CodeBuild for the CI"
  default     = "aws/codebuild/amazonlinux2-x86_64-standard:3.0"
}

variable "codebuild_artifacts_type" {
  type        = string
  description = "The build output artifact's type, default to CODEPIPELINE"
  default     = "CODEPIPELINE"
}

variable "codebuild_source_type" {
  type        = string
  description = "The type of repository that contains the source code to be built. default to CODEPIPELINE "
  default     = "CODEPIPELINE"
}

variable "codebuild_compute_type" {
  type        = string
  description = "Information about the compute resources the build project will use"
  default     = "BUILD_GENERAL1_SMALL"
}

variable "codebuild_privileged_override" {
  type        = bool
  description = "If set to true, enables running the Docker daemon inside a Docker container. Defaults to false. "
  default     = true
}

variable "codebuild_type" {
  type        = string
  description = "The type of build environment to use for related builds, default to LINUX_CONTAINER"
  default     = "LINUX_CONTAINER"
}

variable "codebuild_image_pull_credentials_type" {
  type        = string
  description = "The type of credentials AWS CodeBuild uses to pull images in your build. Available values for this parameter are CODEBUILD or SERVICE_ROLE, default to CODEBUILD"
  default     = "CODEBUILD"
}

variable "slack_chatbot" {
  type        = string
  description = "The name of the slack chatbot that would be used to send notification"
  default     = "pxp-mf-qa-dev-alarms-notify-slack-channel"
}
