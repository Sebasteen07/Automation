# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  valid_workspaces = {
    demo-integrations2-acceptance = "demo-integrations2-acceptance"
    git-taf-prod-mu2-accessibility = "git-taf-prod-mu2-accessibility"
    git-taf-prod-mu2-regression = "git-taf-prod-mu2-regression"
    git-taf-prod-mu2-acceptance = "git-taf-prod-mu2-acceptance"
  }
  selected_workspace = local.valid_workspaces[terraform.workspace]
}
