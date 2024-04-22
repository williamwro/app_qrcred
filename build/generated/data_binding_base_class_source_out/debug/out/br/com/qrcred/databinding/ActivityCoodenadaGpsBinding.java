// Generated by view binder compiler. Do not edit!
package br.com.qrcred.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import br.com.qrcred.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCoodenadaGpsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnDefinirGps;

  @NonNull
  public final ImageButton imageButtonLocation;

  @NonNull
  public final TextView tvLatitude;

  @NonNull
  public final TextView tvLongitude;

  private ActivityCoodenadaGpsBinding(@NonNull LinearLayout rootView, @NonNull Button btnDefinirGps,
      @NonNull ImageButton imageButtonLocation, @NonNull TextView tvLatitude,
      @NonNull TextView tvLongitude) {
    this.rootView = rootView;
    this.btnDefinirGps = btnDefinirGps;
    this.imageButtonLocation = imageButtonLocation;
    this.tvLatitude = tvLatitude;
    this.tvLongitude = tvLongitude;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCoodenadaGpsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCoodenadaGpsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_coodenada_gps, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCoodenadaGpsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnDefinirGps;
      Button btnDefinirGps = ViewBindings.findChildViewById(rootView, id);
      if (btnDefinirGps == null) {
        break missingId;
      }

      id = R.id.imageButtonLocation;
      ImageButton imageButtonLocation = ViewBindings.findChildViewById(rootView, id);
      if (imageButtonLocation == null) {
        break missingId;
      }

      id = R.id.tvLatitude;
      TextView tvLatitude = ViewBindings.findChildViewById(rootView, id);
      if (tvLatitude == null) {
        break missingId;
      }

      id = R.id.tvLongitude;
      TextView tvLongitude = ViewBindings.findChildViewById(rootView, id);
      if (tvLongitude == null) {
        break missingId;
      }

      return new ActivityCoodenadaGpsBinding((LinearLayout) rootView, btnDefinirGps,
          imageButtonLocation, tvLatitude, tvLongitude);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
