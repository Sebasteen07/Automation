# Powershell Script to mass update on terraform workspace
# File name : .\pxp_mass_update_tf_workspace_script.ps1

[CmdletBinding()]
param (
    [Parameter()]
    [string]
    $value = "No changes. Your infrastructure matches the configuration.",
    $apply = $false #Pass parameter as $true to apply changes eg: .\pxp_mass_update_tf_workspace_script.ps1 -apply $true
)


$null = ""
[string] $list=terraform workspace list
[string] $plan_path = "~\plan_dir"
[string] $apply_path = "~\apply_dir"

#Folders to be created in user home directory if doesn't exist
if(!(Test-Path -PathType Container $plan_path))
{
    New-Item -ItemType Directory -Path $plan_path
    Write-Output "$plan_path doesn't exist and it has created"
}

if(!(Test-Path -PathType Container $apply_path))
{
    New-Item -ItemType Directory -Path $apply_path
    Write-Output "$apply_path doesn't exist and it has created"
}

$all = Read-Host "Do you want to apply for all jobs (yes/no)"

if( $all.ToLower() -eq 'yes' ) {
    $json = Get-Content 'all_workspace.txt'
}
elseif( $all.ToLower() -eq 'no' ) {
    $json = Get-Content 'selected_workspace.txt'
}
else {
    Write-Output "Type either 'yes' or 'no' to execute"
    exit 1
}


function workinworkspace($lcconvert) {
    $ErrorActionPreference = 'stop'
    try {
        terraform.exe workspace select $lcconvert
        if ( $apply -eq $false) {
            Write-Output "Generating plan for workspace : $lcconvert......"
            $planmatch=terraform.exe plan -out terraform.plan_$lcconvert -no-color | Tee-Object ~\plan_dir\terraform-plan-$lcconvert.plan.txt | Select-String -Pattern "No changes. Your"
            Write-host "$planmatch"
        }
    
        
            if ( $apply -eq $true ) {
                #Write-Output "Identified changes in the code and applying changes......"
                Write-Output "Plan has been approved and generating second plan after review for workspace : $lcconvert......"
                #Write-Output "------------------"
                $nochanges = terraform.exe plan -out terraform.plan_$lcconvert -no-color | Tee-Object ~\apply_dir\terraform-plan-$lcconvert.plan.txt | Select-String -Pattern "No changes. Your"
                
                $firstplan = Select-String -Path ~\plan_dir\terraform-plan-$lcconvert.plan.txt -Pattern "destroy" | select-object -ExpandProperty Line
                $secondplan = Select-String -Path ~\apply_dir\terraform-plan-$lcconvert.plan.txt -Pattern "destroy" | select-object -ExpandProperty Line
            
                if ( "$nochanges" -eq $value ) 
                {
                    Write-Output "No changes. Your infrastructure matches the configuration."
                }
                else 
                {
                    if (( $firstplan -eq $secondplan))  
                    {
                        Write-Output "Approved plan and new plan are same and applying changes"
                        Write-Host "$secondplan"
                        $applyplan = terraform apply "terraform.plan_$lcconvert" | Select-String -Pattern "Apply complete!"
                        Write-Host "$applyplan"
                    }
                    else 
                    {
                        Write-Output "Approved plan and new plan are not same"
                    }
                }
    }       
}
        
    catch {
        throw $_.Exception.Message
    }
}

foreach($line in $json) {
    $lcconvert=$line.ToLower() #convert from uppercase to lowercase
    $regex= $list | Select-String -Pattern $lcconvert
    #Write-Output "Workspace : $regex"
    if ( [string] $regex -eq $null ) 
    {
        Write-Output "Workspace $lcconvert doesn't exist and to create workspace use terraform workspace new $lcconvert"
        Write-Output "-----------------------------------------------------------------------------------------------------------------------"
        continue
    }
    else 
    {
        workinworkspace $lcconvert #function call
        Write-Output "-----------------------------------------------------------------------------------------------------------------------"
    }
}