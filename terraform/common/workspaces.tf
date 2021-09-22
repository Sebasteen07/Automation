# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

locals {
  valid_workspaces = {
    prod = "prod"
  }
  selected_workspace = local.valid_workspaces[terraform.workspace] # An error on this line, usually indicates an unexpected workspace value
}