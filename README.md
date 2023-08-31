<h1 align="center">Zparc</h1>
<p align="center">
    <a href="https://jitpack.io/#astarivi/Zparc"><img src="https://img.shields.io/badge/download-363d80?logo=android&logoColor=white&style=for-the-badge" alt="Download badge"></a>
</p>

An Android library to create gradient animations, fork of TonnyL [Spark](https://github.com/TonnyL/Spark).
Zparc aims to deliver extra functionality sparkled on top of the original library, while porting it
to Java.

# Preview

<p align="justify">
    <img src="./media/1.gif" width="32%" />
    <img src="./media/2.gif" width="32%" />
    <img src="./media/3.gif" width="32%" />
</p>

## Basic Usage

```java
public class YourActivity extends AppCompatActivity {
    private Zparc zparc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zparc = new Zparc.Builder(this)
                .setDuration(4000)
                .setView(view)
                .setAnimColors(Zparc.ANIM_GREEN_PURPLE)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();

        zparc.startAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        zparc.stopAnimation();
    }
}
```

## Installation

### From Jitpack

```groovy
repositories {
    ...
    maven {
        url 'https://jitpack.io'
    }
}

dependencies {
    ...
    implementation 'com.github.astarivi:Zparc:1.1'
}
```


