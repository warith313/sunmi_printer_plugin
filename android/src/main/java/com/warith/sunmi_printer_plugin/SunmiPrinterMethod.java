package com.warith.sunmi_printer_plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.ArrayList;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.ILcdCallback;
import woyou.aidlservice.jiuiv5.IWoyouService;


public class SunmiPrinterMethod {

    // private final String TAG = SunmiPrinterMethod.class.getSimpleName();
    private final ArrayList<Boolean> _printingText = new ArrayList<>();
    private IWoyouService _woyouService;
    private final Context _context;

    public SunmiPrinterMethod(Context context) {
        this._context = context;
    }

    private final ServiceConnection connService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                _woyouService = IWoyouService.Stub.asInterface(service);
                String serviceVersion = _woyouService.getServiceVersion();
                Toast.makeText(_context, "Sunmi Printer Service Connected. Version :" + serviceVersion, Toast.LENGTH_LONG).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                Toast.makeText(_context, "Sunmi Printer Service Not Found", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(_context, "Sunmi Printer Service Disconnected", Toast.LENGTH_LONG).show();
        }
    };

    public void bindPrinterService() {
        Intent intent = new Intent();
        intent.setPackage("woyou.aidlservice.jiuiv5");
        intent.setAction("woyou.aidlservice.jiuiv5.IWoyouService");
        _context.bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    public void unbindPrinterService() {
        _context.unbindService(connService);
    }

    public void initPrinter() {
        try {
            _woyouService.printerInit(this._callback());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public int updatePrinter() {
        try {
            return _woyouService.updatePrinterState();
        } catch (RemoteException e) {
            return 0; // error
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public void printText(String text) {

        this._printingText.add(this._printText(text));
    }

    private Boolean _printText(String text) {
        try {
            _woyouService.printText(text, this._callback());
            return true;
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /*public Boolean setAlignment(Integer alignment) {
        try {
            _woyouService.setAlignment(alignment, this._callback());
            return true;
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }*/

    public void setFontSize(int fontSize) {
        try {
            _woyouService.setFontSize(fontSize, this._callback());
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    /*public Boolean setFontBold(Boolean bold) {
        if (bold == null) {
            bold = false;
        }

        byte[] command = new byte[]{0x1B, 0x45, 0x1};

        if (!bold) {
            command = new byte[]{0x1B, 0x45, 0x0};
        }

        try {
            _woyouService.sendRAWData(command, this._callback());
            return true;
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }*/


    public void printColumn(
            String[] stringColumns,
            int[] columnWidth,
            int[] columnAlignment
    ) {


        try {
            _woyouService.printColumnsText(
                    stringColumns,
                    columnWidth,
                    columnAlignment,
                    this._callback()
            );

        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void printImage(Bitmap bitmap) {
        try {
            _woyouService.printBitmap(bitmap, this._callback());
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void cutPaper() {
        try {
            _woyouService.cutPaper(this._callback());
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public String getPrinterSerialNo() {

        try {
            return _woyouService.getPrinterSerialNo();
        } catch (RemoteException e) {
            return "";
        } catch (NullPointerException e) {
            return "NOT FOUND";
        }
    }

    public String getPrinterVersion() {
        try {
            return _woyouService.getPrinterVersion();
        } catch (RemoteException e) {
            return "";
        } catch (NullPointerException e) {
            return "NOT FOUND";
        }
    }

    public int getPrinterPaper() {
        try {
            return _woyouService.getPrinterPaper();
        } catch (RemoteException e) {
            return 1;
        } catch (NullPointerException e) {
            return 1;
        }
    }

    public int getPrinterMode() {
        try {
            return _woyouService.getPrinterMode();
        } catch (RemoteException e) {
            return 3;
        } catch (NullPointerException e) {
            return 3;
        }
    }

    public void openDrawer() {
        try {
            _woyouService.openDrawer(this._callback());
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public Boolean drawerStatus() {
        try {
            return  _woyouService.getDrawerStatus();
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public int timesOpened() {
        try {
            return  _woyouService.getOpenDrawerTimes();
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public void lineWrap(int lines) {
        try {
            _woyouService.lineWrap(lines, this._callback());
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void sendRaw(byte[] bytes) {
        try {
            this._woyouService.sendRAWData(bytes, this._callback());
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void enterPrinterBuffer(Boolean clear) {
        try {
            this._woyouService.enterPrinterBuffer(clear);
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void commitPrinterBuffer() {
        try {
            this._woyouService.commitPrinterBuffer();
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void exitPrinterBuffer(Boolean clear) {
        try {
            this._woyouService.exitPrinterBuffer(clear);
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void setAlignment(int alignment) {
        try {
            _woyouService.setAlignment(alignment, this._callback());
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void printQRCode(String data, int moduleSize, int errorLevel) {
        try {
            _woyouService.printQRCode(data, moduleSize, errorLevel, this._callback());
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void printBarCode(
            String data,
            int barcodeType,
            int textPosition,
            int width,
            int height
    ) {
        try {
            _woyouService.printBarCode(
                    data,
                    barcodeType,
                    height,
                    width,
                    textPosition,
                    this._callback()
            );
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    private ICallback _callback() {
        return new ICallback() {
            @Override
            public void onRunResult(boolean isSuccess) throws RemoteException {
            }

            @Override
            public void onReturnString(String result) throws RemoteException {
            }

            @Override
            public void onRaiseException(int code, String msg) throws RemoteException {
            }

            @Override
            public void onPrintResult(int code, String msg) throws RemoteException {
            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        };
    }

    // LCD METHODS

    public void sendLCDCommand(
            int flag
    ) {
        try {
            _woyouService.sendLCDCommand(
                    flag
            );
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void sendLCDString(
            String string
    ) {
        try {
            _woyouService.sendLCDString(
                    string,
                    this._lcdCallback()
            );
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void sendLCDBitmap(
            android.graphics.Bitmap bitmap
    ) {
        try {
            _woyouService.sendLCDBitmap(
                    bitmap,
                    this._lcdCallback()
            );
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void sendLCDDoubleString(
            String topText,
            String bottomText
    ) {
        try {
            _woyouService.sendLCDDoubleString(
                    topText, bottomText,
                    this._lcdCallback()
            );
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    public void sendLCDFillString(
            String string,
            int size,
            boolean fill
    ) {
        try {
            _woyouService.sendLCDFillString(
                    string, size, fill,
                    this._lcdCallback()
            );
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * Show multi lines text on LCD.
     * @param text Text lines.
     * @param align The weight of the solid content of each line. Like flex.
     */
    public void sendLCDMultiString(
            String[] text,
            int[] align
    ) {
        try {
            _woyouService.sendLCDMultiString(
                    text, align,
                    this._lcdCallback()
            );
        } catch (RemoteException ignored) {
        } catch (NullPointerException ignored) {
        }
    }

    private ILcdCallback _lcdCallback() {
        return new ILcdCallback() {
            @Override
            public IBinder asBinder() {
                return null;
            }

            @Override
            public void onRunResult(boolean show) throws RemoteException {
            }
        };
    }
}
