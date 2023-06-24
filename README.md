# Image Viewer Program

This is a Java Swing-based image viewer program that supports users to browse images.

## Features

- Java version: "16.0.1" (Make sure you have installed and configured the corresponding JDK environment).
- Supports slideshow mode.
- Automatically opens all images in the specified directory (including subfolders).
- Support GIF dynamic display.
- Supports image scaling.
- Shortcut keys to switch to the previous and next image: Left and Right arrows, Up and Down arrows, Page Up and Page Down.
- Support viewing picture list.

## Steps to package and run a Java Swing project

### Create Manifest.txt file

Create a text file called Manifest.txt with the following content:

```
Main-Class: MainApp
Class-Path: ../lib/jackson/jackson-core-2.15.2.jar ../lib/jackson/jackson-databind-2.15.2.jar ../lib/jackson/jackson-annotations-2.15.2 .jar
```

### Create a temporary directory and copy third-party library files

Execute the following command in a terminal to create a temporary directory and copy the third-party library files into it:

```
mkdir tmp;
cp -r ../lib/jackson/*.jar tmp/
```

### Create a JAR file and package the third-party library file

Use the following command to create a JAR file and package the third-party library files in the temporary directory:

```
jar cvfm YourProjectName.jar Manifest.txt *.class components/*.class constants/*.class entities/*.class events/*.class services/*.class utils/*. class -C tmp/ .
```

## Run the JAR file

Run the JAR file with the following command:

```
java -jar YourProjectName.jar
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
- 支持查看图片列表

## 打包和运行 Java Swing 项目的步骤

### 创建 Manifest.txt 文件

创建一个名为 Manifest.txt 的文本文件，其中包含以下内容：

```
Main-Class: MainApp
Class-Path: ../lib/jackson/jackson-core-2.15.2.jar ../lib/jackson/jackson-databind-2.15.2.jar ../lib/jackson/jackson-annotations-2.15.2.jar
```

### 创建临时目录并复制第三方库文件

在终端中执行以下命令，创建一个临时目录并将第三方库文件复制到该目录中：

```
mkdir tmp;
cp -r ../lib/jackson/*.jar tmp/
```

### 创建 JAR 文件并打包第三方库文件

使用以下命令创建 JAR 文件，并将临时目录中的第三方库文件一并打包进去：

```
jar cvfm YourProjectName.jar Manifest.txt *.class components/*.class constants/*.class entities/*.class events/*.class services/*.class utils/*.class -C tmp/ .
```

## 运行 JAR 文件

使用以下命令运行 JAR 文件：

```
java -jar YourProjectName.jar
```
