package com.ezeapi.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eze.api.EzeAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class EzeNativeSampleActivity extends Activity implements
		OnClickListener {
	private final int REQUESTCODE_INIT = 10001;
	private final int REQUESTCODE_PREP = 10002;

	private final int REQUESTCODE_WALLET = 10003;
	private final int REQUESTCODE_CHEQUE = 10004;
	private final int REQUESTCODE_SALE = 10006;
	private final int REQUESTCODE_CASHBACK = 10007;
	private final int REQUESTCODE_CASHATPOS = 10008;
	private final int REQUESTCODE_CASH = 10009;

	private final int REQUESTCODE_SEARCH = 10010;
	private final int REQUESTCODE_VOID = 10011;
	private final int REQUESTCODE_ATTACHSIGN = 10012;
	private final int REQUESTCODE_UPDATE = 10013;
	private final int REQUESTCODE_CLOSE = 10014;

	private EditText txtRefNum, txtAmount, txtAmountCashback, txtName,
			txtMobile, txtEmail, txtChqNum, txtBankCode, txtBankName,
			txtBankACNum, txtChqDate;
	private Button btnInit, btnPrep, btnWalletTxn, btnCardSale,
			btnCardCashback, btnCardCashAtPOS, btnChqTxn, btnCash,
			btnSearchTxn, btnVoidTxn, btnAttachSign, btnUpdate, btnClose;

	private String strTxnId = null, emiID = null;
	private String mandatoryErrMsg = "Please fill up mandatory params.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nativesample);

		btnInit = ((Button) findViewById(R.id.btnInitialize));
		btnInit.setOnClickListener(this);

		btnPrep = ((Button) findViewById(R.id.btnPrepare));
		btnPrep.setOnClickListener(this);

		btnWalletTxn = ((Button) findViewById(R.id.btnWalletTxn));
		btnWalletTxn.setOnClickListener(this);

		btnChqTxn = ((Button) findViewById(R.id.btnChequeTxn));
		btnChqTxn.setOnClickListener(this);

		btnCardSale = ((Button) findViewById(R.id.btnSale));
		btnCardSale.setOnClickListener(this);

		btnCardCashback = ((Button) findViewById(R.id.btnCashback));
		btnCardCashback.setOnClickListener(this);

		btnCardCashAtPOS = ((Button) findViewById(R.id.btnCashAtPOS));
		btnCardCashAtPOS.setOnClickListener(this);

		btnCash = ((Button) findViewById(R.id.btnCashTxn));
		btnCash.setOnClickListener(this);

		btnSearchTxn = ((Button) findViewById(R.id.btnSearchTxn));
		btnSearchTxn.setOnClickListener(this);

		btnVoidTxn = ((Button) findViewById(R.id.btnVoidTxn));
		btnVoidTxn.setOnClickListener(this);

		btnAttachSign = ((Button) findViewById(R.id.btnAttachSignature));
		btnAttachSign.setOnClickListener(this);

		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(this);

		btnClose = ((Button) findViewById(R.id.btnClose));
		btnClose.setOnClickListener(this);

		txtRefNum = (EditText) findViewById(R.id.txtRefNum);
		txtAmount = (EditText) findViewById(R.id.txtAmount);
		txtAmountCashback = (EditText) findViewById(R.id.txtCashbackAmount);
		txtName = (EditText) findViewById(R.id.txtName);
		txtMobile = (EditText) findViewById(R.id.txtMobile);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtChqNum = (EditText) findViewById(R.id.txtChqNum);
		txtBankCode = (EditText) findViewById(R.id.txtBankCode);
		txtBankName = (EditText) findViewById(R.id.txtBankName);
		txtBankACNum = (EditText) findViewById(R.id.txtAcNum);
		txtChqDate = (EditText) findViewById(R.id.txtChqDate);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnInitialize:
			initEzetap();
			break;
		case R.id.btnPrepare:
			prepareEzetap();
			break;
		case R.id.btnWalletTxn:
			doWalletTxn();
			break;
		case R.id.btnChequeTxn:
			doChequeTxn();
			break;
		case R.id.btnSale:
			doSaleTxn();
			break;
		case R.id.btnCashback:
			doCashbackTxn();
			break;
		case R.id.btnCashAtPOS:
			doCashAtPosTxn();
			break;
		case R.id.btnCashTxn:
			doCashTxn();
			break;
		case R.id.btnSearchTxn:
			searchTxn();
			break;
		case R.id.btnVoidTxn:
			voidTxn();
			break;
		case R.id.btnAttachSignature:
			attachSignature();
			break;
		case R.id.btnUpdate:
			updateDevice();
			break;
		case R.id.btnClose:
			closeEzetap();
			break;

		default:
			break;
		}
	}

	private void initEzetap() {
		JSONObject jsonRequest = new JSONObject();
		try {
			jsonRequest.put("demoAppKey",
					"Enter your demo app key");
			jsonRequest.put("prodAppKey",
					"Enter your prod app key");
			jsonRequest.put("merchantName", "Demo");
			jsonRequest.put("userName", "Demo user");
			jsonRequest.put("currencyCode", "INR");
			jsonRequest.put("appMode", "DEMO");
			jsonRequest.put("captureSignature", "false");
			jsonRequest.put("prepareDevice", "true");// Set it true if you want
														// to initialize and
														// prepare device at a
														// time
														// or false if you want
														// to initialize only
														// and prepare device
														// later for card txn.
		} catch (JSONException e) {
			e.printStackTrace();
		}
		EzeAPI.initialize(this, REQUESTCODE_INIT, jsonRequest);
	}

	private void prepareEzetap() {
		EzeAPI.prepareDevice(this, REQUESTCODE_PREP);
	}

	private void doWalletTxn() {
		if (isMandatoryParamsValid()) {
			JSONObject jsonRequest = new JSONObject();
			JSONObject jsonOptionalParams = new JSONObject();
			JSONObject jsonReferences = new JSONObject();
			JSONObject jsonCustomer = new JSONObject();
			try {
				// Building Customer Object
				jsonCustomer.put("name", txtName.getText().toString().trim());
				jsonCustomer.put("mobileNo", txtMobile.getText().toString()
						.trim());
				jsonCustomer.put("email", txtEmail.getText().toString().trim());

				// Building References Object
				jsonReferences.put("reference1", txtRefNum.getText().toString()
						.trim());

				// Building Optional params Object
				jsonOptionalParams.put("references", jsonReferences);
				jsonOptionalParams.put("customer", jsonCustomer);

				// Building final request object
				jsonRequest
						.put("amount", txtAmount.getText().toString().trim());
				jsonRequest.put("options", jsonOptionalParams);

				EzeAPI.walletTransaction(this, REQUESTCODE_WALLET, jsonRequest);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			displayToast(mandatoryErrMsg);
		}
	}

	private void doChequeTxn() {
		if (isMandatoryParamsValidForChq()) {
			JSONObject jsonRequest = new JSONObject();
			JSONObject jsonOptionalParams = new JSONObject();
			JSONObject jsonReferences = new JSONObject();
			JSONObject jsonCheque = new JSONObject();
			JSONObject jsonCustomer = new JSONObject();
			try {
				// Building Customer Object
				jsonCustomer.put("name", txtName.getText().toString().trim());
				jsonCustomer.put("mobileNo", txtMobile.getText().toString()
						.trim());
				jsonCustomer.put("email", txtEmail.getText().toString().trim());

				// Building References Object
				jsonReferences.put("reference1", txtRefNum.getText().toString()
						.trim());

				// Building Cheque Object
				jsonCheque.put("chequeNumber", txtChqNum.getText().toString()
						.trim());
				jsonCheque.put("bankCode", txtBankCode.getText().toString()
						.trim());
				jsonCheque.put("bankName", txtBankName.getText().toString()
						.trim());
				jsonCheque.put("bankAccountNo", txtBankACNum.getText()
						.toString().trim());
				jsonCheque.put("chequeDate", txtChqDate.getText().toString()
						.trim());

			
				jsonOptionalParams.put("references", jsonReferences);
				jsonOptionalParams.put("customer", jsonCustomer);

				// Building final request object
				jsonRequest
						.put("amount", txtAmount.getText().toString().trim());
			
				jsonRequest.put("options", jsonOptionalParams);
				jsonRequest.put("cheque", jsonCheque);

				EzeAPI.chequeTransaction(this, REQUESTCODE_CHEQUE, jsonRequest);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else
			displayToast(mandatoryErrMsg);
	}

	private void doSaleTxn() {
		if (isMandatoryParamsValid()) {
			JSONObject jsonRequest = new JSONObject();
			JSONObject jsonOptionalParams = new JSONObject();
			JSONObject jsonReferences = new JSONObject();
			JSONObject jsonCustomer = new JSONObject();
			try {
				// Building Customer Object
				jsonCustomer.put("name", txtName.getText().toString().trim());
				jsonCustomer.put("mobileNo", txtMobile.getText().toString()
						.trim());
				jsonCustomer.put("email", txtEmail.getText().toString().trim());

				// Building References Object
				jsonReferences.put("reference1", txtRefNum.getText().toString()
						.trim());

				// Building Optional params Object
				jsonOptionalParams.put("amountCashback", 0.00);// Cannot have
																// amount
																// cashback in
																// SALE
																// transaction.
				jsonOptionalParams.put("amountTip", 0.00);
				jsonOptionalParams.put("references", jsonReferences);
				jsonOptionalParams.put("customer", jsonCustomer);

				// Building final request object
				jsonRequest
						.put("amount", txtAmount.getText().toString().trim());
				jsonRequest.put("mode", "SALE");// This attributes determines
												// the type of transaction
				jsonRequest.put("options", jsonOptionalParams);

				EzeAPI.cardTransaction(this, REQUESTCODE_SALE, jsonRequest);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else
			displayToast(mandatoryErrMsg);
	}

	private void doCashbackTxn() {
		if (isMandatoryParamsValid()) {
			JSONObject jsonRequest = new JSONObject();
			JSONObject jsonOptionalParams = new JSONObject();
			JSONObject jsonReferences = new JSONObject();
			JSONObject jsonCustomer = new JSONObject();
			try {
				// Building Customer Object
				jsonCustomer.put("name", txtName.getText().toString().trim());
				jsonCustomer.put("mobileNo", txtMobile.getText().toString()
						.trim());
				jsonCustomer.put("email", txtEmail.getText().toString().trim());

				// Building References Object
				jsonReferences.put("reference1", txtRefNum.getText().toString()
						.trim());

				// Building Optional params Object
				jsonOptionalParams.put("amountCashback", txtAmountCashback
						.getText().toString().trim());
				jsonOptionalParams.put("amountTip", 0.00);
				jsonOptionalParams.put("references", jsonReferences);
				jsonOptionalParams.put("customer", jsonCustomer);

				// Building final request object
				jsonRequest
						.put("amount", txtAmount.getText().toString().trim());
				jsonRequest.put("mode", "CASHBACK");// This attributes
													// determines the type of
													// transaction
				jsonRequest.put("options", jsonOptionalParams);

				EzeAPI.cardTransaction(this, REQUESTCODE_CASHBACK, jsonRequest);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else
			displayToast(mandatoryErrMsg);
	}

	private void doCashAtPosTxn() {
		if (isMandatoryParamsValid()) {
			JSONObject jsonRequest = new JSONObject();
			JSONObject jsonOptionalParams = new JSONObject();
			JSONObject jsonReferences = new JSONObject();
			JSONObject jsonCustomer = new JSONObject();
			try {
				// Building Customer Object
				jsonCustomer.put("name", txtName.getText().toString().trim());
				jsonCustomer.put("mobileNo", txtMobile.getText().toString()
						.trim());
				jsonCustomer.put("email", txtEmail.getText().toString().trim());

				// Building References Object
				jsonReferences.put("reference1", txtRefNum.getText().toString()
						.trim());

				// Building Optional params Object
				jsonOptionalParams.put("amountCashback", txtAmountCashback
						.getText().toString().trim());
				jsonOptionalParams.put("amountTip", 0.00);
				jsonOptionalParams.put("references", jsonReferences);
				jsonOptionalParams.put("customer", jsonCustomer);

				// Building final request object
				jsonRequest.put("amount", 0.00);// Cannot have amount for
												// CASH@POS transaction.
				jsonRequest.put("mode", "CASH@POS");// This attributes
													// determines the type of
													// transaction
				jsonRequest.put("options", jsonOptionalParams);

				EzeAPI.cardTransaction(this, REQUESTCODE_CASHATPOS, jsonRequest);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else
			displayToast(mandatoryErrMsg);
	}

	private void doCashTxn() {
		if (isMandatoryParamsValid()) {
			JSONObject jsonRequest = new JSONObject();
			JSONObject jsonOptionalParams = new JSONObject();
			JSONObject jsonReferences = new JSONObject();
			JSONObject jsonCustomer = new JSONObject();
			try {
				// Building Customer Object
				jsonCustomer.put("name", txtName.getText().toString().trim());
				jsonCustomer.put("mobileNo", txtMobile.getText().toString()
						.trim());
				jsonCustomer.put("email", txtEmail.getText().toString().trim());

				// Building References Object
				jsonReferences.put("reference1", txtRefNum.getText().toString()
						.trim());

				// Building Optional params Object
				jsonOptionalParams.put("amountCashback", 0.00);// Cannot have
																// amount
																// cashback in
																// cash
																// transaction.
				jsonOptionalParams.put("amountTip", 0.00);
				jsonOptionalParams.put("references", jsonReferences);
				jsonOptionalParams.put("customer", jsonCustomer);

				// Building final request object
				jsonRequest
						.put("amount", txtAmount.getText().toString().trim());
				jsonRequest.put("options", jsonOptionalParams);
				Log.d("param", jsonRequest.toString());

				EzeAPI.cashTransaction(this, REQUESTCODE_CASH, jsonRequest);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else
			displayToast(mandatoryErrMsg);
	}

	private void searchTxn() {
		JSONObject jsonRequest = new JSONObject();
		try {
			jsonRequest.put("agentName", "Demo User");
			jsonRequest.put("startDate", "1/1/2015");
			jsonRequest.put("endDate", "12/31/2015");
			jsonRequest.put("txnType", "cash");
			jsonRequest.put("txnStatus", "void");
			EzeAPI.searchTransaction(this, REQUESTCODE_SEARCH, jsonRequest);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void voidTxn() {
		if (isTransactionIdValid()) {
			EzeAPI.voidTransaction(this, REQUESTCODE_VOID, strTxnId);
		} else
			displayToast("Inorrect txn Id, please make a Txn.");
	}

	private void attachSignature() {
		if (isTransactionIdValid()) {
			JSONObject jsonRequest = new JSONObject();
			JSONObject jsonImageObj = new JSONObject();
			try {
				// Building Image Object
				jsonImageObj
						.put("imageData",
								"The Base64 Image bitmap string of your siganture goes here");// Cannot
																								// have
																								// amount
																								// for
																								// CASH@POS
																								// transaction.
				jsonImageObj.put("imageType", "JPEG");
				jsonImageObj.put("height", "");// optional
				jsonImageObj.put("weight", "");// optional

				// Building final request object
				jsonRequest.put("emiId", emiID);// pass this field if you have
												// an
												// email Id associated with the
												// transaction
				jsonRequest.put("tipAmount", 0.00);// optional
				// jsonRequest.put("image", jsonImageObj); // Pass this
				// attribute when you have a valid captured signature
				jsonRequest.put("txnId", strTxnId);

				EzeAPI.attachSignature(this, REQUESTCODE_ATTACHSIGN,
						jsonRequest);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else
			displayToast("Inorrect txn Id, please make a Txn.");
	}

	private void updateDevice() {
		EzeAPI.updateDevice(this, REQUESTCODE_UPDATE);
	}

	private void closeEzetap() {
		EzeAPI.close(this, REQUESTCODE_CLOSE);
	}

	private boolean isMandatoryParamsValid() {
		if (txtAmount.getText().toString().equalsIgnoreCase("")
				|| txtRefNum.getText().toString().equalsIgnoreCase(""))
			return false;
		else
			return true;
	}

	private boolean isMandatoryParamsValidForChq() {
		if (txtAmount.getText().toString().equalsIgnoreCase("")
				|| txtChqDate.getText().toString().equalsIgnoreCase("")
				|| txtBankACNum.getText().toString().equalsIgnoreCase("")
				|| txtBankName.getText().toString().equalsIgnoreCase("")
				|| txtBankCode.getText().toString().equalsIgnoreCase("")
				|| txtChqNum.getText().toString().equalsIgnoreCase("")
				|| txtRefNum.getText().toString().equalsIgnoreCase(""))
			return false;
		else
			return true;
	}

	private boolean isTransactionIdValid() {
		if (strTxnId == null)
			return false;
		else
			return true;
	}

	private void displayToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (intent != null && intent.hasExtra("response"))
			Toast.makeText(this, intent.getStringExtra("response"),
					Toast.LENGTH_SHORT).show();
		switch (requestCode) {
		case REQUESTCODE_CASH:
		case REQUESTCODE_CASHBACK:
		case REQUESTCODE_CASHATPOS:
		case REQUESTCODE_WALLET:
		case REQUESTCODE_SALE:
		case REQUESTCODE_UPDATE:
			if (resultCode == RESULT_OK) {
				try {
					JSONObject response = new JSONObject(
							intent.getStringExtra("response"));
					response = response.getJSONObject("result");
					response = response.getJSONObject("txn");
					strTxnId = response.getString("txnId");
					emiID = response.getString("emiId");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
