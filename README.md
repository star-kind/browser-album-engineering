# Image Viewer Program

This is a Java Swing-based image viewer program that supports users to browse images.

## Features

- Java version: "16.0.1" (Make sure you have installed and configured the corresponding JDK environment).
- Supports slideshow mode.
- Automatically opens all images in the specified directory (including subfolders).
- Support GIF dynamic display.
- Supports image scaling.
- Shortcut keys to switch to the previous and next image: Left and Right arrows, Up and Down arrows, Page Up and Page Down.

## How to Package as JAR File

To package the program as a JAR file, follow these steps:

1. Compile the program into `.class` files using the following command:

```
javac MainApp.java
```

2. Create a text file named `Manifest.txt` and specify the program's entry class in it. As follows:

```
Main-Class: MainApp
```

3. Package the program as a JAR file using the following command:

```
jar -cvmf ./Manifest.txt ../your-app.jar *.class
```

Replace `your-app.jar` with the desired name for your JAR file, and `*.class` includes all compiled `.class` files.

4. Run the JAR program with the following command:

```
java -jar ../app.jar
```

<hr>

# 图片阅览器程序

这是一个基于 Java Swing 的图片阅览器程序，支持用户用以浏览图片。

## 功能特点

- Java 版本： "16.0.1"（请确保已安装和配置相应版本的 JDK 环境）。
- 支持幻灯片模式。
- 自动打开指定目录下的全部图片（包括子文件夹）。
- 支持 GIF 动态显示。
- 支持图片缩放。
- 切换上一张和下一张图片的快捷键：左右箭头、上下箭头、Page Up 和 Page Down。

## 如何打包为 JAR 文件

要将程序打包为 JAR 文件，请按照以下步骤操作：

1. 使用以下命令将程序编译为 `.class` 文件：

```
javac MainApp.java
```

2. 创建一个名为 `Manifest.txt` 的文本文件，并在其中指定程序的入口类，如下：

```
Main-Class: MainApp
```

3. 使用以下命令将程序打包为 JAR 文件：

```
jar -cvmf ./Manifest.txt ../app.jar *.class
```

`*.class` 表示包含所有编译后的 `.class` 文件。

4. 使用以下命令运行 JAR 程序：

```
java -jar ../app.jar
```
