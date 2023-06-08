import 'enums.dart';

///*SunmiStyle*
///
///With this class you can build your own layout to print some text.
///The thext can come already with [align], [fontSize] and *bold*, and then you don't need to type 3 commands to do the same THING!

class SunmiStyle {
  SunmiStyle({this.fontSize, this.align, this.bold});

  SunmiPrintAlign? align;
  bool? bold;
  SunmiFontSize? fontSize;
}