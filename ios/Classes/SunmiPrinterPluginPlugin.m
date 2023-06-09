#import "SunmiPrinterPluginPlugin.h"
#if __has_include(<sunmi_printer_plugin/sunmi_printer_plugin-Swift.h>)
#import <sunmi_printer_plugin/sunmi_printer_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "sunmi_printer_plugin-Swift.h"
#endif

@implementation SunmiPrinterPluginPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftSunmiPrinterPluginPlugin registerWithRegistrar:registrar];
}
@end
