# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  valid_workspaces = {
    demo-integrations2-acceptance      = "demo-integrations2-acceptance"
    git-taf-prod-mu2-accessibility     = "git-taf-prod-mu2-accessibility"
    git-taf-prod-mu2-regression        = "git-taf-prod-mu2-regression"
    git-taf-prod-mu2-acceptance        = "git-taf-prod-mu2-acceptance"
    prod-patientportal-regression1     = "prod-patientportal-regression1"
    prod-patientportal-regression2     = "prod-patientportal-regression2"
    prod-patientportal-regression3     = "prod-patientportal-regression3"
    prod-patientportal-regression4     = "prod-patientportal-regression4"
    git-taf-prod-precheck              = "git-taf-prod-precheck"
    git-taf-prod-precheck-rsdk-patient = "git-taf-prod-precheck-rsdk-patient"
    prod-forms-regression1             = "prod-forms-regression1"
    prod-forms-regression2             = "prod-forms-regression2"
    prod-integrations2-acceptance      = "prod-integrations2-acceptance"
    prod-integrations2-regression1     = "prod-integrations2-regression1"
    prod-integrations2-regression2     = "prod-integrations2-regression2"
    prod-integrations2-regression3     = "prod-integrations2-regression3"
    git-taf-prod-pss-acceptance        = "git-taf-prod-pss-acceptance"
    demo-integrations2-regression1     = "demo-integrations2-regression1"
    demo-integrations2-regression2     = "demo-integrations2-regression2"
    demo-integrations2-regression3     = "demo-integrations2-regression3"
    prod-integrations-acceptance-oauth10 = "prod-integrations-acceptance-oauth10"
    prod-integrations1-acceptance      = "prod-integrations1-acceptance"
    prod-integrations1-regression      = "prod-integrations1-regression"
    demo-integrations-acceptance-oauth10 = "demo-integrations-acceptance-oauth10"
    demo-integrations1-acceptance      = "demo-integrations1-acceptance"
    demo-integrations1-regression      = "demo-integrations1-regression"
  }
  selected_workspace = local.valid_workspaces[terraform.workspace]
}
