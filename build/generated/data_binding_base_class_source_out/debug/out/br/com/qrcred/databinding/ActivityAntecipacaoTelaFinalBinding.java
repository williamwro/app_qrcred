// Generated by view binder compiler. Do not edit!
package br.com.qrcred.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import br.com.qrcred.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAntecipacaoTelaFinalBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton btnRetornar;

  @NonNull
  public final TextView textView64;

  @NonNull
  public final TextView textView66;

  @NonNull
  public final TextView textView67;

  @NonNull
  public final TextView textView69;

  @NonNull
  public final TextView tvValoPedido;

  private ActivityAntecipacaoTelaFinalBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton btnRetornar, @NonNull TextView textView64,
      @NonNull TextView textView66, @NonNull TextView textView67, @NonNull TextView textView69,
      @NonNull TextView tvValoPedido) {
    this.rootView = rootView;
    this.btnRetornar = btnRetornar;
    this.textView64 = textView64;
    this.textView66 = textView66;
    this.textView67 = textView67;
    this.textView69 = textView69;
    this.tvValoPedido = tvValoPedido;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAntecipacaoTelaFinalBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAntecipacaoTelaFinalBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_antecipacao_tela_final, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAntecipacaoTelaFinalBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnRetornar;
      MaterialButton btnRetornar = ViewBindings.findChildViewById(rootView, id);
      if (btnRetornar == null) {
        break missingId;
      }

      id = R.id.textView64;
      TextView textView64 = ViewBindings.findChildViewById(rootView, id);
      if (textView64 == null) {
        break missingId;
      }

      id = R.id.textView66;
      TextView textView66 = ViewBindings.findChildViewById(rootView, id);
      if (textView66 == null) {
        break missingId;
      }

      id = R.id.textView67;
      TextView textView67 = ViewBindings.findChildViewById(rootView, id);
      if (textView67 == null) {
        break missingId;
      }

      id = R.id.textView69;
      TextView textView69 = ViewBindings.findChildViewById(rootView, id);
      if (textView69 == null) {
        break missingId;
      }

      id = R.id.tvValoPedido;
      TextView tvValoPedido = ViewBindings.findChildViewById(rootView, id);
      if (tvValoPedido == null) {
        break missingId;
      }

      return new ActivityAntecipacaoTelaFinalBinding((ConstraintLayout) rootView, btnRetornar,
          textView64, textView66, textView67, textView69, tvValoPedido);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
