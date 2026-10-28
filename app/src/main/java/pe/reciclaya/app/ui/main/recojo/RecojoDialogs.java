package pe.reciclaya.app.ui.main.recojo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import java.util.function.BiConsumer;
import pe.reciclaya.app.R;

/** Dialog creation, fonts and form widgets; actions are delegated to the ViewModel. */
public final class RecojoDialogs {
    private final Context context;
    private AlertDialog dialog;
    public RecojoDialogs(Context context) { this.context=context; }
    public void confirm(Runnable action) {
        dismiss();
        dialog=new AlertDialog.Builder(context).setTitle("¿Terminar reciclaje?")
            .setMessage("Confirma cuando hayan entregado y recibido los residuos. Ambos deberán confirmar y calificarse.")
            .setNegativeButton("No",null).setPositiveButton("Sí",(d,which)->action.run()).show();
        style(dialog);
    }
    public void cancel(RecojoMapPresentation presentation,Runnable action) {
        dismiss();
        dialog=new AlertDialog.Builder(context).setTitle(presentation.cancelTitle()).setMessage(presentation.cancelDescription())
            .setNegativeButton("Volver",null).setPositiveButton("Sí, cancelar",(d,which)->action.run()).show();
        style(dialog);
    }
    public void rate(BiConsumer<Integer,String> action) {
        dismiss();
        LinearLayout content=new LinearLayout(context); content.setOrientation(LinearLayout.VERTICAL); content.setPadding(dp(24),dp(12),dp(24),dp(12));
        RatingBar stars=new RatingBar(context); stars.setNumStars(5); stars.setStepSize(1); stars.setRating(0);
        content.addView(stars,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        EditText comment=new EditText(context); comment.setHint("Reseña opcional"); comment.setMaxLines(3);
        comment.setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(1000)}); content.addView(comment);
        dialog=new AlertDialog.Builder(context).setTitle("Califica a la otra persona").setView(content).setNegativeButton("Más tarde",null).setPositiveButton("Guardar",null).create();
        final AlertDialog ratingDialog=dialog;
        ratingDialog.setOnShowListener(d -> {
            style(ratingDialog);
            ratingDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                if (stars.getRating()<1) { Toast.makeText(context,"Selecciona de 1 a 5 estrellas.",Toast.LENGTH_SHORT).show(); return; }
                ratingDialog.dismiss(); action.accept((int)stars.getRating(),comment.getText().toString());
            });
        });
        ratingDialog.show();
    }
    private int dp(int value) { return Math.round(value*context.getResources().getDisplayMetrics().density); }
    private void font(View view) {
        if (view instanceof TextView) ((TextView)view).setTypeface(ResourcesCompat.getFont(context,R.font.gotham_medium));
        if (view instanceof ViewGroup) { ViewGroup group=(ViewGroup)view; for (int i=0;i<group.getChildCount();i++) font(group.getChildAt(i)); }
    }
    private void style(AlertDialog alert) {
        if (alert.getWindow()!=null) font(alert.getWindow().getDecorView());
        TextView title=alert.findViewById(androidx.appcompat.R.id.alertTitle);
        if (title!=null) title.setTypeface(ResourcesCompat.getFont(context,R.font.gotham_bold));
        for (int which:new int[]{AlertDialog.BUTTON_POSITIVE,AlertDialog.BUTTON_NEGATIVE}) {
            Button button=alert.getButton(which); if (button==null) continue;
            boolean primary=which==AlertDialog.BUTTON_POSITIVE;
            androidx.core.view.ViewCompat.setBackgroundTintList(button,null); button.setBackgroundTintList(null);
            button.setBackgroundResource(primary?R.drawable.button_final_green_background:R.drawable.button_final_green_border);
            button.setTextColor(ContextCompat.getColor(context,primary?R.color.white:R.color.verde));
            button.setTypeface(ResourcesCompat.getFont(context,R.font.gotham_bold)); button.setAllCaps(false); button.setTextSize(14);
            button.setMinHeight(dp(48)); button.setPadding(dp(16),dp(12),dp(16),dp(12));
            ViewGroup.LayoutParams params=button.getLayoutParams();
            if (params instanceof ViewGroup.MarginLayoutParams) { ((ViewGroup.MarginLayoutParams)params).setMarginStart(dp(8)); button.setLayoutParams(params); }
        }
    }
    public void dismiss() { if (dialog!=null) { dialog.dismiss(); dialog=null; } }
}
