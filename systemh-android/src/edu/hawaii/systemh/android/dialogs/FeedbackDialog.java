package edu.hawaii.systemh.android.dialogs;

import edu.hawaii.systemh.android.R;
import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.widget.TextView;

/**
 * A dialog that pops up when the connection test failed.
 * @author Group H
 *
 */
public class FeedbackDialog extends Dialog {

  /**
   * The constructor.
   * @param context The context.
   */
  public FeedbackDialog(Context context) {
    super(context);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.dialog);

    TextView feedback = (TextView) findViewById(R.id.dialogText);

    setTitle("Connection Failed");
    feedback.setText("Please check the IP address of the running frontend" +
        "\n\n\nTouch outside to cancel");
    
    this.setCancelable(true);
    setCanceledOnTouchOutside(true);
    
  }
 }
