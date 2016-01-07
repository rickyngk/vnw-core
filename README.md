# vnw-core
Some utility function for Vietnamworks based project. 

##Dependency: 

  + Volley: https://android.googlesource.com/platform/frameworks/volley/
  + Android helper: https://github.com/rickyngk/android-helper
  + Volley helper: https://github.com/rickyngk/volley-helper

##To use this module:

Step-1: Checkout as submodules

    $cd your_android_project
    $git submodule add https://android.googlesource.com/platform/frameworks/volley
    $git submodule add https://github.com/rickyngk/android-helper.git
    $git submodule add https://github.com/rickyngk/volley-helper.git
  
Step-2: Add to settings.gradle

    include ':app', ':android-helper', ':volley', ':volley-helper', ':vnw-core'
  
Step-3: Modify build.gradle of app, add:

    compile project(':android-helper')
    compile project(':vnw-core')
    compile project(':card-stack-view')
  
