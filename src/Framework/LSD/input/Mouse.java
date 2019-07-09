package Framework.LSD.input;

import javafx.scene.input.MouseButton;

public enum Mouse {

    LEFT(MouseButton.PRIMARY),
    MIDDLE(MouseButton.MIDDLE),
    RIGHT(MouseButton.SECONDARY);


    private final MouseButton mouseButton;

    Mouse(MouseButton mouseButton) {
        this.mouseButton = mouseButton;
    }

    public MouseButton getMouseButton() {
        return mouseButton;
    }

    public static Mouse find(MouseButton mouseButton) {
        for (Mouse m :
                values()) {
            if (m.mouseButton != null && m.mouseButton == mouseButton) {
                return m;
            }
        }
        return null;
    }
}
