# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  valid_workspaces = {
    #Subfolder-env-XmlFile format to maintain uniqueness
    #piIntPlatformAcceptance-demo-codebuild = "piIntPlatformAcceptance-demo-codebuild"
    default = "default"
  }
  selected_workspace = local.valid_workspaces[terraform.workspace]
}
