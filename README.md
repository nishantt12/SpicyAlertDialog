Spicy Alert Dialog
===================

## Setup
The simplest way to use SpicyAlertDialog is to add the library as aar dependency to your build.

**Maven**

   <dependency>
     <groupId>com.nishant.spicyalertdialog</groupId>
     <artifactId>spicyalertdialog</artifactId>
     <version>1.0.3</version>
     <type>pom</type>
   </dependency>

**Gradle**


    dependencies {
        compile 'com.nishant.spicyalertdialog:spicyalertdialog:1.0.3'
    }

## Usage

      new SpicyAlertDialog(MainActivity.this, 1)
                              .setCustomImage(R.drawable.piggy_graphic)
                              .setTitleText("my title")
                              .setContentText("My content")
                              .setCancelClickListener(new SpicyAlertDialog.OnSpicyClickListener() {
                                  @Override
                                  public void onClick(SpicyAlertDialog spicyAlertDialog) {
      
      
                                  }
                              })
                              .setConfirmClickListener(new SpicyAlertDialog.OnSpicyClickListener() {
                                  @Override
                                  public void onClick(SpicyAlertDialog spicyAlertDialog) {
      
      
      
                                  }
                              })
                              .show();



