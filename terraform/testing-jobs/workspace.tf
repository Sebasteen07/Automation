# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  valid_workspaces = {
    demo-integrations2-acceptance        = "demo-integrations2-acceptance"
    git-taf-prod-mu2-accessibility       = "git-taf-prod-mu2-accessibility"
    git-taf-prod-mu2-regression          = "git-taf-prod-mu2-regression"
    git-taf-prod-mu2-acceptance          = "git-taf-prod-mu2-acceptance"
    prod-patientportal-regression1       = "prod-patientportal-regression1"
    prod-patientportal-regression2       = "prod-patientportal-regression2"
    prod-patientportal-regression3       = "prod-patientportal-regression3"
    prod-patientportal-regression4       = "prod-patientportal-regression4"
    git-taf-prod-precheck                = "git-taf-prod-precheck"
    prod-forms-regression1               = "prod-forms-regression1"
    prod-forms-regression2               = "prod-forms-regression2"
    prod-integrations2-acceptance        = "prod-integrations2-acceptance"
    prod-integrations2-regression1       = "prod-integrations2-regression1"
    prod-integrations2-regression2       = "prod-integrations2-regression2"
    prod-integrations2-regression3       = "prod-integrations2-regression3"
    git-taf-prod-pss-acceptance          = "git-taf-prod-pss-acceptance"
    demo-integrations2-regression1       = "demo-integrations2-regression1"
    demo-integrations2-regression2       = "demo-integrations2-regression2"
    demo-integrations2-regression3       = "demo-integrations2-regression3"
    prod-integrations-acceptance-oauth10 = "prod-integrations-acceptance-oauth10"
    prod-integrations1-acceptance        = "prod-integrations1-acceptance"
    prod-integrations1-regression        = "prod-integrations1-regression"
    demo-integrations-acceptance-oauth10 = "demo-integrations-acceptance-oauth10"
    demo-integrations1-acceptance        = "demo-integrations1-acceptance"
    demo-integrations1-regression        = "demo-integrations1-regression"
    prod-practiceportal-regression1      = "prod-practiceportal-regression1"
    git-taf-prod-p2p-directmessage       = "git-taf-prod-p2p-directmessage"
    git-taf-prod-rcm-acceptance          = "git-taf-prod-rcm-acceptance"
    prod-practiceportal-regression2      = "prod-practiceportal-regression2"
    prod-sitegen-regression              = "prod-sitegen-regression"
    demo-ng-integration-pf-acceptance    = "demo-ng-integration-pf-acceptance"
    demo-ng-int-pf-acceptance-index      = "demo-ng-int-pf-acceptance-index"
    demo-ng-int-pf-acceptance-payment    = "demo-ng-int-pf-acceptance-payment"
    demo-ng-int-pf-regression            = "demo-ng-int-pf-regression"
    demo-practiceportal-regression1      = "demo-practiceportal-regression1"
    demo-practiceportal-regression2      = "demo-practiceportal-regression2"
    demo-sitegen-regression              = "demo-sitegen-regression"
    demo-pss-at-acceptance               = "demo-pss-at-acceptance"
    demo-pss-ge-acceptance               = "demo-pss-ge-acceptance"
    demo-pss-geadapter-acceptance        = "demo-pss-geadapter-acceptance"
    demo-pss-gw-acceptance               = "demo-pss-gw-acceptance"
    demo-pss-ng-acceptance               = "demo-pss-ng-acceptance"
    demo-pss-ngadapter-acceptance        = "demo-pss-ngadapter-acceptance"
    demo-payreporting-e2e-element        = "demo-payreporting-e2e-element"
    demo-payreporting-e2e-paypal         = "demo-payreporting-e2e-paypal"
    demo-payreporting-e2e-qbms           = "demo-payreporting-e2e-qbms"
    demo-payreporting-payments           = "demo-payreporting-payments"
    demo-payreporting-smoke              = "demo-payreporting-smoke"
    demo-precheck                        = "demo-precheck"
    demo-rcm-acceptance                  = "demo-rcm-acceptance"
    dev3-pss-api-geadapter-acceptance    = "dev3-pss-api-geadapter-acceptance"
    dev3-pss-api-ngadapter-acceptance    = "dev3-pss-api-ngadapter-acceptance"
    dev3-pss-regression1                 = "dev3-pss-regression1"
    qa1-integration-acceptance           = "qa1-integration-acceptance"
    qa1-integration-acceptance-2_0       = "qa1-integration-acceptance-2_0"
    qa1-integration-acceptance-oauth10   = "qa1-integration-acceptance-oauth10"
    qa1-integration-regression           = "qa1-integration-regression"
    qa1-pi-integration-regression        = "qa1-pi-integration-regression"
    qa1-practice-portal-acceptance       = "qa1-practice-portal-acceptance"
    qa1-sitegen-regression               = "qa1-sitegen-regression"
    dev3-integrations2-acceptance        = "dev3-integrations2-acceptance"
    dev3-integrations2-regression1       = "dev3-integrations2-regression1"
    dev3-integrations2-regression2       = "dev3-integrations2-regression2"
    dev3-integrations2-regression3       = "dev3-integrations2-regression3"
    dev3-integrations-acceptance-oauth10 = "dev3-integrations-acceptance-oauth10"
    dev3-integrations1-acceptance        = "dev3-integrations1-acceptance"
    dev3-integrations1-regression        = "dev3-integrations1-regression"
    dev3-payreporting-e2e-element        = "dev3-payreporting-e2e-element"
    dev3-payreporting-payments           = "dev3-payreporting-payments"
    demo-patient-portal-regression1      = "demo-patient-portal-regression1"
    demo-patient-portal-regression2      = "demo-patient-portal-regression2"
    demo-patient-portal-regression3      = "demo-patient-portal-regression3"
    demo-patient-portal-regression4      = "demo-patient-portal-regression4"
    dev3-pss20-acceptance                = "dev3-pss20-acceptance"
    dev3-sitegen-regression              = "dev3-sitegen-regression"
    dev3-ng-int-platform-regression      = "dev3-ng-int-platform-regression"
    dev3-rcm-precheck-acceptance         = "dev3-rcm-precheck-acceptance"
    demo-forms-regression1               = "demo-forms-regression1"
    demo-forms-regression2               = "demo-forms-regression2"
    dev3-forms-regression1               = "dev3-forms-regression1"
    dev3-forms-regression2               = "dev3-forms-regression2"
    dev3-patientportal-regression1       = "dev3-patientportal-regression1"
    dev3-patientportal-regression2       = "dev3-patientportal-regression2"
    dev3-patientportal-regression3       = "dev3-patientportal-regression3"
    dev3-patientportal-regression4       = "dev3-patientportal-regression4"
    qa1-forms-regression1                = "qa1-forms-regression1"
    qa1-forms-regression2                = "qa1-forms-regression2"
  }
  selected_workspace = local.valid_workspaces[terraform.workspace]
}
