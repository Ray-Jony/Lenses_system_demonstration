package MainProgram.view;

import Framework.LSD.views.HomeView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;


/**
 * Class MainHomeView is created on 19/08/2019 19:13.
 *
 * @author Ray
 * @version 19/08/2019
 **/

public class Home extends HomeView {
    @Override
    public void launch() {
        getController().getChromaticAberrationBtn().setOnAction(e -> gotoView("Chromatic Aberration"));
        getController().getZoomingEffectBtn().setOnAction(e -> gotoView("Zooming Effect"));
        getController().getSphericalAberrationBtn().setOnAction(e -> gotoView("Spherical Aberration"));
        getController().getFreeModeBtn().setOnAction(e -> gotoView("Free Mode"));

        getController().getChromaticAberrationBtn().hoverProperty().addListener((observableValue, aBoolean, t1) -> {

            if (t1) {
                getController().getChromaticAberrationBtn()
                        .setStyle("-fx-background-color: linear-gradient(to right, red 0%, green 50%,blue 100%);" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 8;" +
                                "-jfx-buttontype: flat");
                getController().getDescriptionTitle().setText("Chromatic Aberration");
                getController().getDescriptionImage()
                        .setImage(new Image("Framework/LSD/views/IMAGE/chromatic_aberration.jpg"));
                getController().getDescription().setText("In optics, chromatic aberration (abbreviated CA; also called chromatic distortion and spherochromatism) is a failure of a lens to focus all colors to the same point.[1] It is caused by dispersion: the refractive index of the lens elements varies with the wavelength of light. The refractive index of most transparent materials decreases with increasing wavelength.[2] Since the focal length of a lens depends on the refractive index, this variation in refractive index affects focusing.[3] Chromatic aberration manifests itself as \"fringes\" of color along boundaries that separate dark and bright parts of the image.");
            }else {
                getController().getChromaticAberrationBtn()
                        .setStyle("-fx-background-color: transparent;" +
                                "-fx-text-fill: black;" +
                                "-fx-background-radius: 8;" +
                                "-jfx-buttontype: flat");
            }
        });

        getController().getZoomingEffectBtn().hoverProperty().addListener((observableValue, aBoolean, t1) -> {

            if (t1) {
                getController().getZoomingEffectBtn()
                        .setStyle("-fx-background-color: #a5beff;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 8;" +
                                "-jfx-buttontype: flat");
                getController().getDescriptionTitle().setText("Zooming Effect");
                getController().getDescriptionImage()
                        .setImage(new Image("Framework/LSD/views/IMAGE/zoom_lens.png"));
                getController().getDescription().setText("A zoom lens is a mechanical assembly of lens elements for which the focal length (and thus angle of view) can be varied, as opposed to a fixed focal length (FFL) lens (see prime lens).\n" +
                        "\n" +
                        "A true zoom lens, also called a parfocal lens, is one that maintains focus when its focal length changes.[1] A lens that loses focus during zooming is more properly called a varifocal lens. Despite being marketed as zoom lenses, virtually all consumer lenses with variable focal lengths use varifocal design.\n" +
                        "\n" +
                        "The convenience of variable focal length comes at the cost of complexity – and some compromises on image quality, weight, dimensions, aperture, autofocus performance, and cost. For example, all zoom lenses suffer from at least slight, if not considerable, loss of image resolution at their maximum aperture, especially at the extremes of their focal length range. This effect is evident in the corners of the image, when displayed in a large format or high resolution. The greater the range of focal length a zoom lens offers, the more exaggerated these compromises must become.[2]");
            }else {
                getController().getZoomingEffectBtn()
                        .setStyle("-fx-background-color: transparent;" +
                                "-fx-text-fill: black;" +
                                "-fx-background-radius: 8;" +
                                "-jfx-buttontype: flat");
            }
        });

        getController().getSphericalAberrationBtn().hoverProperty().addListener((observableValue, aBoolean, t1) -> {

            if (t1) {
                getController().getSphericalAberrationBtn()
                        .setStyle("-fx-background-color: #ffa6b1;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 8;" +
                                "-jfx-buttontype: flat");
                getController().getDescriptionTitle().setText("Spherical Aberration");
                getController().getDescriptionImage()
                        .setImage(new Image("Framework/LSD/views/IMAGE/spherical_aberration.png"));
                getController().getDescription()
                        .setText("Spherical aberration is a type of aberration found in optical systems that use elements with spherical surfaces. Lenses and curved mirrors are most often made with surfaces that are spherical, because this shape is easier to form than non-spherical curved surfaces. Light rays that strike a spherical surface off-centre are refracted or reflected more or less than those that strike close to the centre. This deviation reduces the quality of images produced by optical systems.");
            }else {
                getController().getSphericalAberrationBtn()
                        .setStyle("-fx-background-color: transparent;" +
                                "-fx-text-fill: black;" +
                                "-fx-background-radius: 8;" +
                                "-jfx-buttontype: flat");
            }
        });

        getController().getFreeModeBtn().hoverProperty().addListener((observableValue, aBoolean, t1) -> {

            if (t1) {
                getController().getFreeModeBtn()
                        .setStyle("-fx-background-color: #82f7ff;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 8;" +
                                "-jfx-buttontype: flat");
                getController().getDescriptionTitle().setText("Free Mode");
                getController().getDescriptionImage()
                        .setImage(null);
                getController().getDescription()
                        .setText("In this mode you can customize the lens and lights");
            }else {
                getController().getFreeModeBtn()
                        .setStyle("-fx-background-color: transparent;" +
                                "-fx-text-fill: black;" +
                                "-fx-background-radius: 8;" +
                                "-jfx-buttontype: flat");
            }
        });

    }

}
