public class ControlGroup {
    private Model model;
    private Vue vue;
    public ControlButton controlButton;
    public ControlMenu controlMenu;

    public ControlGroup(Model model){
        this.model = model;

        vue = new Vue(model);

        controlButton = new ControlButton(vue, model);
        controlMenu = new ControlMenu(vue);

        vue.display();
    }
}