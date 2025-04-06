package Ewallet;

import Ewallet.model.WalletSystem;
import Ewallet.service.ApplicationService;
import Ewallet.service.impl.ApplicationServiceImpl;

public class Main {
    public static void main(String[] args) {
        WalletSystem walletSystem = new WalletSystem();
        ApplicationService applicationService = new ApplicationServiceImpl(walletSystem);
        applicationService.start();
    }
}
