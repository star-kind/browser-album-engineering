package services;

import constants.*;

public class ScaleFactorHandler {
  public static double zoom_out_factor = 1.1;
  public static double zoom_in_factor = 0.9;

  public Double processDecision(Double decision) {
    return updateScalingFactor(decision);
  }

  public Double updateScalingFactor(Double decision) {
    Double calculate = 1.0;

    if (decision.equals(Constants.zoom_in_decision)) {
      calculateZoomInFactor(decision);
      calculate = zoom_in_factor;

    } else if (decision.equals(Constants.zoom_out_decision)) {
      calculateZoomOutFactor(decision);
      calculate = zoom_out_factor;

    }

    System.out.println(this + " updateScalingFactor calculate=" + calculate);
    return calculate;
  }

  public void calculateZoomInFactor(Double decision) {
    zoom_in_factor = decision * zoom_in_factor;
    if (zoom_in_factor > Constants.zoom_upper_limit) {
      zoom_in_factor = Constants.zoom_upper_limit;
    }
  }

  public void calculateZoomOutFactor(Double decision) {
    zoom_out_factor = decision * zoom_out_factor;
    if (zoom_out_factor < Constants.zoom_lower_limit) {
      zoom_out_factor = Constants.zoom_lower_limit;
    }
  }

}