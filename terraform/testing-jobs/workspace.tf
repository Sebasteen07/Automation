# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  valid_workspaces = {
    demo-integrations2-acceptance = "demo-integrations2-acceptance"
  }
  selected_workspace = local.valid_workspaces[terraform.workspace]
}
