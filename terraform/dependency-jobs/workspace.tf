# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  valid_workspaces = {
    default = "default"
  }
  selected_workspace = local.valid_workspaces[terraform.workspace]
}
