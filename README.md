# PriTask

## Libraries

### Download javaFx 16
Link: https://gluonhq.com/products/javafx/
JavaFx 16 is located in the latest release section.

### Add global library (IntelliJ)
- Press File in top left corner.
- Select Project Structure.
- Goto Global libraries.
- Add new global library by pressing the plus sign and select java.
- Find and open javaFx directory until the "lib" directory is found.
- Select lib and press OK.

### Add VM options (IntelliJ)
- Press Run in the options toolbar.
- Press Goto Configurations.
- Press "Modify options".
- Select "Add VM Options".
- In the newly added textfield add: --module-path "Replace this with the filepath to javaFx/lib" --add-modules javafx.controls,javafx.fxml

Example: --module-path /home/ORIM/JavaFX/openjfx-16_linux-x64_bin-sdk/javafx-sdk-16/lib --add-modules javafx.controls,javafx.fxml

- press confirm and save.


### Enable Maven (IntelliJ)
- Open pom.xml in IDE. On the far right, open the Maven tab and press "Reload all Maven Projects"