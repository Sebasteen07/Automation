# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
resource "aws_codepipeline" "main" {
  name     = local.name
  role_arn = aws_iam_role.pipeline.arn

  artifact_store {
    location = local.pipeline_artifact_bucket_name
    type     = "S3"
  }

  stage {
    name = "Source"

    action {
      name             = "Source"
      category         = "Source"
      owner            = "AWS"
      provider         = "CodeCommit"
      version          = "1"
      output_artifacts = ["SourceZip"]

      configuration = {
        BranchName           = try(local.inputs[terraform.workspace].codecommit_branch)
        RepositoryName       = var.repository_name
        PollForSourceChanges = try(local.inputs[terraform.workspace].PollForSourceChanges)
      }
    }
  }

  stage {
    name = "Build"

    action {
      name             = "CodeBuild"
      category         = "Build"
      owner            = "AWS"
      provider         = "CodeBuild"
      input_artifacts  = ["SourceZip"]
      output_artifacts = ["BuiltZip"]
      version          = "1"

      configuration = {
        ProjectName = module.build.codebuild_project.name
      }
    }
  }
}
