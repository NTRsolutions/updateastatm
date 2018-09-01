package com.atm.ast.astatm.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.utils.ASTUIUtil;

public class PMCheckLIstFragment extends MainFragment {
    Button btnDone;
    CheckBox cbEarthVolt;
    EditText etEarthVolt;
    CheckBox cbBattTopup;
    EditText etBattTopup;
    CheckBox cbBattCell;
    EditText etBattCell;
    CheckBox cbCharger;
    EditText etCharger;
    CheckBox cbInverter;
    EditText etInverter;
    CheckBox cbEbConn;
    EditText etEbConn;
    CheckBox cbConn;
    EditText etConn;
    CheckBox cbSolar;
    EditText etSolar;
    CheckBox cbCell1;
    EditText etCell1;
    CheckBox cbCell2;
    EditText etCell2;
    CheckBox cbCell3;
    EditText etCell3;
    CheckBox cbCell4;
    EditText etCell4;
    CheckBox cbCell5;
    EditText etCell5;
    CheckBox cbCell6;
    EditText etCell6;
    CheckBox cbCell7;
    EditText etCell7;
    CheckBox cbCell8;
    EditText etCell8;
    CheckBox cbPhotoYes;
    CheckBox cbPhotoNo;
    CheckBox cbModemConnectionYes;
    CheckBox cbModemConnectionNo;
    CheckBox cbSpareRequirement;
    EditText etSpareRequirement;
    CheckBox cbBatteryTerminal;
    EditText etBatteryTerminal;
    CheckBox cbSolarStruct;
    EditText etSolarStruct;
    CheckBox cbComm;
    CheckBox cbNonComm;
    CheckBox cbBankOfficial;
    CheckBox cbGuard;
    String strEarthVolt = "NA";
    String strBattTopup = "NA";
    String strBattCells = "NA";
    String strCharger = "NA";
    String strInverter = "NA";
    String strEbConn = "NA";
    String strConn = "NA";
    String strSolar = "NA";
    String strSignOff = "NA";
    String strCell1 = "NA";
    String strCell2 = "NA";
    String strCell3 = "NA";
    String strCell4 = "NA";
    String strCell5 = "NA";
    String strCell6 = "NA";
    String strCell7 = "NA";
    String strCell8 = "NA";
    String strConnectionAndTightness = "NA";
    String strSolarCleaning = "NA";
    String strSolarStructureAndPanelTightness = "NA";
    String strBatteryTerminalGreasing = "NA";
    String strPhotos = "NA";
    String strModemConn = "NA";
    String strCommStatus = "NA";
    String strSpareRequirement = "NA";

    @Override
    protected int fragmentLayout() {
        return R.layout.pm_checklist_fragment;
    }

    @Override
    protected void loadView() {
        this.btnDone = this.findViewById(R.id.btnDone);
        this.cbEarthVolt = this.findViewById(R.id.cbEarthVolt);
        this.etEarthVolt = this.findViewById(R.id.etEarthVolt);
        this.cbBattTopup = this.findViewById(R.id.cbBattTopup);
        this.etBattTopup = this.findViewById(R.id.etBattTopup);
        this.cbBattCell = this.findViewById(R.id.cbBattCell);
        this.etBattCell = this.findViewById(R.id.etBattCell);
        this.cbCharger = this.findViewById(R.id.cbCharger);
        this.etCharger = this.findViewById(R.id.etCharger);
        this.cbInverter = this.findViewById(R.id.cbInverter);
        this.etInverter = this.findViewById(R.id.etInverter);
        this.cbEbConn = this.findViewById(R.id.cbEbConn);
        this.etEbConn = this.findViewById(R.id.etEbConn);
        this.cbConn = this.findViewById(R.id.cbConn);
        this.etConn = this.findViewById(R.id.etConn);
        this.cbSolar = this.findViewById(R.id.cbSolar);
        this.etSolar = this.findViewById(R.id.etSolar);
        this.cbCell1 = this.findViewById(R.id.cbCell1);
        this.etCell1 = this.findViewById(R.id.etCell1);
        this.cbCell2 = this.findViewById(R.id.cbCell2);
        this.etCell2 = this.findViewById(R.id.etCell2);
        this.cbCell3 = this.findViewById(R.id.cbCell3);
        this.etCell3 = this.findViewById(R.id.etCell3);
        this.cbCell4 = this.findViewById(R.id.cbCell4);
        this.etCell4 = this.findViewById(R.id.etCell4);
        this.cbCell5 = this.findViewById(R.id.cbCell5);
        this.etCell5 = this.findViewById(R.id.etCell5);
        this.cbCell6 = this.findViewById(R.id.cbCell6);
        this.etCell6 = this.findViewById(R.id.etCell6);
        this.cbCell7 = this.findViewById(R.id.cbCell7);
        this.etCell7 = this.findViewById(R.id.etCell7);
        this.cbCell8 = this.findViewById(R.id.cbCell8);
        this.etCell8 = this.findViewById(R.id.etCell8);
        this.cbPhotoYes = this.findViewById(R.id.cbPhotoYes);
        this.cbPhotoNo = this.findViewById(R.id.cbPhotoNo);
        this.cbModemConnectionYes = this.findViewById(R.id.cbModemConnectionYes);
        this.cbModemConnectionNo = this.findViewById(R.id.cbModemConnectionNo);
        this.cbSpareRequirement = this.findViewById(R.id.cbSpareRequirement);
        this.etSpareRequirement = this.findViewById(R.id.etSpareRequirement);
        etSpareRequirement.setEnabled(false);
        this.cbBatteryTerminal = this.findViewById(R.id.cbBatteryTerminal);
        this.etBatteryTerminal = this.findViewById(R.id.etBatteryTerminal);
        etBatteryTerminal.setEnabled(false);
        this.cbSolarStruct = this.findViewById(R.id.cbSolarStruct);
        this.etSolarStruct = this.findViewById(R.id.etSolarStruct);
        etSolarStruct.setEnabled(false);
        this.cbComm = this.findViewById(R.id.cbComm);
        this.cbNonComm = this.findViewById(R.id.cbNonComm);
        this.cbBankOfficial = this.findViewById(R.id.cbBankOfficial);
        this.cbGuard = this.findViewById(R.id.cbGuard);
        etCell8.setEnabled(false);
        etEbConn.setEnabled(false);
        etCharger.setEnabled(false);
        etBattCell.setEnabled(false);
        etBattTopup.setEnabled(false);
        etEarthVolt.setEnabled(false);
        etInverter.setEnabled(false);
        etSolar.setEnabled(false);
        etConn.setEnabled(false);
        etCell1.setEnabled(false);
        etCell3.setEnabled(false);
        etCell2.setEnabled(false);
        etCell4.setEnabled(false);
        etCell5.setEnabled(false);
        etCell6.setEnabled(false);
        etCell7.setEnabled(false);

    }

    @Override
    protected void setClickListeners() {
        cbBatteryTerminal.setOnClickListener(this);
        cbSolarStruct.setOnClickListener(this);
        cbSpareRequirement.setOnClickListener(this);
        cbComm.setOnClickListener(this);
        cbNonComm.setOnClickListener(this);
        cbBankOfficial.setOnClickListener(this);
        cbGuard.setOnClickListener(this);
        cbEarthVolt.setOnClickListener(this);
        cbBattTopup.setOnClickListener(this);
        cbBattCell.setOnClickListener(this);
        cbCharger.setOnClickListener(this);
        cbInverter.setOnClickListener(this);
        cbEbConn.setOnClickListener(this);
        cbConn.setOnClickListener(this);
        cbSolar.setOnClickListener(this);
        cbCell1.setOnClickListener(this);
        cbCell2.setOnClickListener(this);
        cbCell3.setOnClickListener(this);
        cbCell4.setOnClickListener(this);
        cbCell5.setOnClickListener(this);
        cbCell6.setOnClickListener(this);
        cbCell7.setOnClickListener(this);
        cbCell8.setOnClickListener(this);
        cbPhotoNo.setOnClickListener(this);
        cbPhotoYes.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cbBatteryTerminal) {
            if (cbBatteryTerminal.isChecked()) {
                etBatteryTerminal.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etBatteryTerminal.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbSolarStruct) {
            if (cbSolarStruct.isChecked()) {
                etSolarStruct.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etSolarStruct.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbSpareRequirement) {
            if (cbSpareRequirement.isChecked()) {
                etSpareRequirement.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etSpareRequirement.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbComm) {
            if (cbComm.isChecked()) {
                cbNonComm.setChecked(false);
                strCommStatus = "Comm";
            } else {
            }
        } else if (view.getId() == R.id.cbNonComm) {
            if (cbNonComm.isChecked()) {
                cbComm.setChecked(false);
                strCommStatus = "Non Comm";
            } else {

            }
        } else if (view.getId() == R.id.cbBankOfficial) {
            if (cbBankOfficial.isChecked()) {
                cbGuard.setChecked(false);
                strSignOff = "Bank Official";
            } else {
            }
        } else if (view.getId() == R.id.cbGuard) {
            if (cbGuard.isChecked()) {
                cbBankOfficial.setChecked(false);
                strSignOff = "Guard";
            } else {

            }
        } else if (view.getId() == R.id.cbEarthVolt) {
            if (cbEarthVolt.isChecked()) {
                etEarthVolt.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etEarthVolt.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbBattTopup) {
            if (cbBattTopup.isChecked()) {
                strBattTopup = "1";
                etBattTopup.setEnabled(true);
            } else {
                strBattTopup = "0";
                etBattTopup.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbBattCell) {
            if (cbBattCell.isChecked()) {
                strBattCells = "1";
                etBattCell.setEnabled(true);
            } else {
                strBattCells = "0";
                etBattCell.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbCharger) {
            if (cbCharger.isChecked()) {
                strCharger = "1";
                etCharger.setEnabled(true);
            } else {
                strCharger = "0";
                etCharger.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbInverter) {
            if (cbInverter.isChecked()) {
                strInverter = "1";
                etInverter.setEnabled(true);
            } else {
                strInverter = "0";
                etInverter.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbEbConn) {
            if (cbEbConn.isChecked()) {
                strEbConn = "1";
                etEbConn.setEnabled(true);
            } else {
                strEbConn = "0";
                etEbConn.setEnabled(true);
            }
        } else if (view.getId() == R.id.cbConn) {
            if (cbConn.isChecked()) {
                strConn = "1";
                strConnectionAndTightness = "1";
                etConn.setEnabled(true);
            } else {
                strConn = "0";
                strConnectionAndTightness = "0";
                etConn.setEnabled(true);
            }
        } else if (view.getId() == R.id.cbSolar) {
            if (cbSolar.isChecked()) {
                strSolar = "1";
                etSolar.setEnabled(true);
            } else {
                strSolar = "0";
                etSolar.setEnabled(true);
            }
        } else if (view.getId() == R.id.cbCell1) {
            if (cbCell1.isChecked()) {
                etCell1.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etCell1.setEnabled(false);
            }
        }

        /*cbSignOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbSignOff.isChecked()) {
                    strSignOff = "1";
                    etSignOff.setEnabled(true);
                } else {
                    strSignOff = "0";
                    etSignOff.setEnabled(true);
                }
            }
        });
*/
        else if (view.getId() == R.id.cbCell2) {
            if (cbCell2.isChecked()) {
                etCell2.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etCell2.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbCell3) {
            if (cbCell3.isChecked()) {
                etCell3.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etCell3.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbCell4) {
            if (cbCell4.isChecked()) {
                etCell4.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etCell4.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbCell5) {
            if (cbCell5.isChecked()) {
                etCell5.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etCell5.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbCell6) {
            if (cbCell6.isChecked()) {
                etCell6.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etCell6.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbCell7) {
            if (cbCell7.isChecked()) {
                etCell7.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etCell7.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbCell8) {
            if (cbCell8.isChecked()) {
                etCell8.setEnabled(true);
                //strEarthVolt = etEarthVolt.getText().toString();
            } else {
                etCell8.setEnabled(false);
            }
        } else if (view.getId() == R.id.cbPhotoNo) {
            if (cbPhotoNo.isChecked()) {
                cbPhotoYes.setChecked(false);
                //strSignOff = "Bank Official";
            } else {

            }
        } else if (view.getId() == R.id.cbPhotoYes) {
            if (cbPhotoYes.isChecked()) {
                cbPhotoNo.setChecked(false);
                //strSignOff = "Bank Official";
            } else {

            }
        } else if (view.getId() == R.id.btnDone) {
            doneButtonActionPerform();
        }

    }

    public void doneButtonActionPerform() {
        strEarthVolt = etEarthVolt.getText().toString();
        strInverter = etInverter.getText().toString();
        strBattCells = etBattCell.getText().toString();
        strCell1 = etCell1.getText().toString();
        strCell2 = etCell2.getText().toString();
        strCell3 = etCell3.getText().toString();
        strCell4 = etCell4.getText().toString();
        strCell5 = etCell5.getText().toString();
        strCell6 = etCell6.getText().toString();
        strCell7 = etCell7.getText().toString();
        strCell8 = etCell8.getText().toString();
        strBatteryTerminalGreasing = etBatteryTerminal.getText().toString();
        //strPhotos = etPhotos.getText().toString();
        strSolarStructureAndPanelTightness = etSolarStruct.getText().toString();
        //strModemConn = etModemConnection.getText().toString();
        strSpareRequirement = etSpareRequirement.getText().toString();
        strEarthVolt = etEarthVolt.getText().toString();
        strBattTopup = etBattTopup.getText().toString();
        strBattCells = etBattCell.getText().toString();
        strCharger = etCharger.getText().toString();
        strInverter = etInverter.getText().toString();
        strSolarCleaning = etSolar.getText().toString();
        strEbConn = etEbConn.getText().toString();
        if ((cbEarthVolt.isChecked() && strEarthVolt.equals("")) || !cbEarthVolt.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for Earthing Volt");
        } else if ((cbInverter.isChecked() && strInverter.equals("")) || !cbInverter.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for Inverter/PCU");
        } else if (cbBattCell.isChecked() && strBattCells.equals("")) {
            ASTUIUtil.showToast("Please Provide Input for Battery Cells");
        } else if ((cbCell1.isChecked() && strCell1.equals("")) || !cbCell1.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for S.G. Cell 1");
        } else if ((cbCell2.isChecked() && strCell2.equals("")) || !cbCell2.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for S.G. Cell 2");
        } else if ((cbCell3.isChecked() && strCell3.equals("")) || !cbCell3.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for S.G. Cell 3");
        } else if ((cbCell4.isChecked() && strCell4.equals("")) || !cbCell4.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for S.G. Cell 4");
        } else if ((cbInverter.isChecked() && strInverter.equals("")) || !cbInverter.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for Inverter/PCU");
        } else if ((cbEbConn.isChecked() && strEbConn.equals("")) || !cbEbConn.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for Eb Connection");
        } else if ((cbConn.isChecked() && strConn.equals("")) || !cbConn.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for Connection and Tightness");
        } else if ((cbSolar.isChecked() && strSolarCleaning.equals("")) || !cbSolar.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for Solar Cleaning");
        } /*else if ((cbSolarStruct.isChecked() && strSolarStructureAndPanelTightness.equals("")) || !cbSolarStruct.isChecked()) {
                    ASTUIUtil.showToast( "Please Provide Input for Solar Structure and Panel Tightness");
                }*/ else if ((cbBattTopup.isChecked() && strBattTopup.equals("")) || !cbBattTopup.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for Battery Topup");
        } else if ((cbBattCell.isChecked() && strBattCells.equals("")) || !cbBattCell.isChecked()) {
            ASTUIUtil.showToast("Please Provide Input for Battery Cells");
        } /*else if (!cbBankOfficial.isChecked() && !cbGuard.isChecked()) {
                    ASTUIUtil.showToast( "Please Select SignOff");
                }*/ else if (!cbComm.isChecked() && !cbNonComm.isChecked()) {
            ASTUIUtil.showToast("Please Select Comm Status");
        } else if (!cbModemConnectionYes.isChecked() && !cbModemConnectionNo.isChecked()) {
            ASTUIUtil.showToast("Please Select Modem Connection");
        } else if (!cbPhotoYes.isChecked() && !cbPhotoNo.isChecked()) {
            ASTUIUtil.showToast("Please Select Photo Availability");
        } else {
                    /*if (strPhotos.equals("") && !cbPhotos.isChecked()) {
                        strPhotos = "NA";
                    } else {
                        //strPhotos = "";
                    }*/
            if (cbPhotoYes.isChecked()) {
                strPhotos = "Yes";
            } else if (cbPhotoNo.isChecked()) {
                strPhotos = "No";
            }

            if (cbModemConnectionYes.isChecked()) {
                strModemConn = "Yes";
            } else if (cbModemConnectionNo.isChecked()) {
                strModemConn = "No";
            }

            if (strBatteryTerminalGreasing.equals("") && !cbBatteryTerminal.isChecked()) {
                strBatteryTerminalGreasing = "NA";
            } else {
                //strBatteryTerminalGreasing = "";
            }


            if (strSolarStructureAndPanelTightness.equals("") && !cbSolarStruct.isChecked()) {
                strSolarStructureAndPanelTightness = "NA";
            } else {
                //strSolarStructureAndPanelTightness = "";
            }
                    /*if (strModemConn.equals("") && !cbModemConnection.isChecked()) {
                        strModemConn = "NA";
                    } else {
                        //strModemConn = "";
                    }*/


            if (strSpareRequirement.equals("") && !cbSpareRequirement.isChecked()) {
                strSpareRequirement = "NA";
            } else {
                //strSpareRequirement = "";
            }
            if (strEarthVolt.equals("") && !cbEarthVolt.isChecked()) {
                strEarthVolt = "NA";
            } else {
                //strEarthVolt = "";
            }

            if (strBattTopup.equals("") && !cbBattTopup.isChecked()) {
                strBattTopup = "NA";
            } else {
                //strBattTopup = "";
            }

            if (strBattCells.equals("") && !cbBattCell.isChecked()) {
                strBattCells = "NA";
            } else {
                //strBattCells = "";
            }

            if (strCharger.equals("") && !cbCharger.isChecked()) {
                strCharger = "NA";
            } else {
                //strCharger = "";
            }

            if (strInverter.equals("") && !cbInverter.isChecked()) {
                strInverter = "NA";
            } else {
                //strInverter = "";
            }

            if (strEbConn.equals("") && !cbEbConn.isChecked()) {
                strEbConn = "NA";
            } else {
                //strEbConn = "";
            }

                /*strConn = etConn.getText().toString();
                if (strConn.equals("") && !cbConn.isChecked()) {
                    strConn = "NA";
                } else {
                    strConn = "^^";
                }*/

            strSolar = etSolar.getText().toString();
            if (strSolar.equals("") && !cbSolar.isChecked()) {
                strSolar = "NA";
            } else {
                //strSolar = "";
            }

//                strSignOff = etSignOff.getText().toString();
//                if (strSignOff.equals("") && !cbSignOff.isChecked()) {
//                    strSignOff = "NA";
//                } else {
//                    strSignOff = "^^";
//                }

            strCell1 = etCell1.getText().toString();
            if (strCell1.equals("") && !cbCell1.isChecked()) {
                strCell1 = "NA";
            } else {
                //strCell1 = "";
            }


            strCell2 = etCell2.getText().toString();
            if (strCell2.equals("") && !cbCell2.isChecked()) {
                strCell2 = "NA";
            } else {
                //strCell2 = "";
            }
            strCell3 = etCell3.getText().toString();
            if (strCell3.equals("") && !cbCell3.isChecked()) {
                strCell3 = "NA";
            } else {
                //strCell3 = "";
            }
            strCell4 = etCell4.getText().toString();
            if (strCell4.equals("") && !cbCell4.isChecked()) {
                strCell4 = "NA";
            } else {
                //strCell4 = "";
            }
            strCell5 = etCell5.getText().toString();
            if (strCell5.equals("") && !cbCell5.isChecked()) {
                strCell5 = "NA";
            } else {
                //strCell5 = "";
            }


            strCell6 = etCell6.getText().toString();
            if (strCell6.equals("") && !cbCell6.isChecked()) {
                strCell6 = "NA";
            } else {
                //strCell6 = "";
            }
            strCell7 = etCell7.getText().toString();
            if (strCell7.equals("") && !cbCell7.isChecked()) {
                strCell7 = "NA";
            } else {
                //strCell7 = "";
            }
            strCell8 = etCell8.getText().toString();
            if (strCell8.equals("") && !cbCell8.isChecked()) {
                strCell8 = "NA";
            } else {
                //strCell8 = "";
            }
            strSpareRequirement = etSpareRequirement.getText().toString();
            if (strSpareRequirement.equals("") && !cbSpareRequirement.isChecked()) {
                strSpareRequirement = "NA";
            } else {
                // strSpareRequirement = "";
            }

            Intent intent = new Intent("ViewPageChange");
            intent.putExtra("willReloadBackScreen", true);
            intent.putExtra("strEarthVolt", strEarthVolt);
            intent.putExtra("strInverter", strInverter);
            intent.putExtra("strBattCells", strBattCells);
            intent.putExtra("strCell1", strCell1);
            intent.putExtra("strCell2", strCell2);
            intent.putExtra("strCell3", strCell3);
            intent.putExtra("strCell4", strCell4);
            intent.putExtra("strCell5", strCell5);
            intent.putExtra("strCell6", strCell6);
            intent.putExtra("strCell7", strCell7);
            intent.putExtra("strCell8", strCell8);
            intent.putExtra("strBatteryTerminalGreasing", strBatteryTerminalGreasing);
            intent.putExtra("strSolarStructureAndPanelTightness", strSolarStructureAndPanelTightness);
            intent.putExtra("strSpareRequirement", strSpareRequirement);
            intent.putExtra("strCharger", strCharger);
            intent.putExtra("strSolarCleaning", strSolarCleaning);
            intent.putExtra("strEbConn", strEbConn);
            intent.putExtra("strPhotos", strPhotos);
            intent.putExtra("strModemConn", strModemConn);
            intent.putExtra("strBattTopup", strBattTopup);
            intent.putExtra("strSolar", strSolar);
            intent.putExtra("isChecked", false);
            reloadBackScreen(intent);
        }
    }

    boolean willReloadOnBack = true;

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();

    }
   /*   for save this into server
   arrSaveData[11] = strEarthVolt;
    arrSaveData[12] = strBattTopup;
    arrSaveData[13] = strBattCells;
    arrSaveData[14] = strCharger;
    arrSaveData[15] = strInverter;
    arrSaveData[16] = strEbConn;
    arrSaveData[17] = strConn;
    arrSaveData[18] = strSolar;
    arrSaveData[19] = strSignOff;
    arrSaveData[20] = strCell1;
    arrSaveData[21] = strCell2;
    arrSaveData[22] = strCell3;
    arrSaveData[23] = strCell4;
    arrSaveData[24] = strCell5;
    arrSaveData[25] = strCell6;
    arrSaveData[26] = strCell7;
    arrSaveData[27] = strCell8;
    arrSaveData[42] = strPhotos;
    arrSaveData[43] = strModemConn;
    arrSaveData[44] = strSpareRequirement;
    arrSaveData[45] = strBatteryTerminalGreasing;
    arrSaveData[46] = strSolarStructureAndPanelTightness;*/
}
