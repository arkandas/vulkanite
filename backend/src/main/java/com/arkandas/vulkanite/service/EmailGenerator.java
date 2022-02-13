package com.arkandas.vulkanite.service;

import com.arkandas.vulkanite.model.db.User;
import com.arkandas.vulkanite.model.db.Wallet;

public class EmailGenerator {

    public static String userRegistrationEmail(User user, Wallet wallet){
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en-GB\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Registration Successful</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "      a[x-apple-data-detectors] {\n" +
                "        color: inherit !important;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body style=\"margin: 0; padding: 0\">\n" +
                "    <table\n" +
                "      role=\"presentation\"\n" +
                "      border=\"0\"\n" +
                "      cellpadding=\"0\"\n" +
                "      cellspacing=\"0\"\n" +
                "      width=\"100%\"\n" +
                "    >\n" +
                "      <tr>\n" +
                "        <td style=\"padding: 0px 0 0px 0\">\n" +
                "          <table\n" +
                "            align=\"center\"\n" +
                "            border=\"0\"\n" +
                "            cellpadding=\"0\"\n" +
                "            cellspacing=\"0\"\n" +
                "            width=\"493\"\n" +
                "            style=\"border-collapse: collapse; border: 1px solid #cccccc\"\n" +
                "          >\n" +
                "            <tr>\n" +
                "              <td\n" +
                "                align=\"center\"\n" +
                "                bgcolor=\"#70bbd9\"\n" +
                "                style=\"padding: 0px 0 0px 0\"\n" +
                "              >\n" +
                "                <img\n" +
                "                  src=\"https://github.com/arkandas/vulkanite-backend/blob/master/assets/banner.png\"\n" +
                "                  width=\"493\"\n" +
                "                  height=\"279\"\n" +
                "                  style=\"display: block\"\n" +
                "                />\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px\">\n" +
                "                <table\n" +
                "                  border=\"0\"\n" +
                "                  cellpadding=\"0\"\n" +
                "                  cellspacing=\"0\"\n" +
                "                  width=\"100%\"\n" +
                "                  style=\"border-collapse: collapse\"\n" +
                "                >\n" +
                "                  <tr>\n" +
                "                    <td style=\"color: #153643; font-family: Arial, sans-serif\">\n" +
                "                      <h1 style=\"font-size: 24px; margin: 0\">\n" +
                "                        Welcome to Vulkanite App!\n" +
                "                      </h1>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td\n" +
                "                      style=\"\n" +
                "                        color: #153643;\n" +
                "                        font-family: Arial, sans-serif;\n" +
                "                        font-size: 16px;\n" +
                "                        line-height: 24px;\n" +
                "                        padding: 20px 0 30px 0;\n" +
                "                      \"\n" +
                "                    >\n" +
                "                      <h4 style=\"margin: 0\">\n" +
                "                        Hello " + user.getUserfullname() +"\n" +
                "                      </h4>\n" +
                "                      <p style=\"margin: 7\">\n" +
                "                        Thank you for creating an account. This is your Vulcanite address:\n" +
                "                      </p>\n" +
                "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"font-family: sans-serif; font-size: 16px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"> <a target=\"_blank\" style=\"display: inline-block; color: #ffffff; background-color: #3498db; border: solid 1px #3498db; border-radius: 5px; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 14px; font-weight: bold; margin: 0; padding: 12px 25px; text-transform: capitalize; border-color: #3498db;\">" + wallet.getWalletAddress() + "</a> </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "                      <p style=\"margin: 5\">\n" +
                "                       Share this address with anyone and they can send you payments.\n" +
                "                      </p>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td>\n" +
                "            <tr>\n" +
                "              <td bgcolor=\"#ee4c50\" style=\"padding: 15px 15px\">\n" +
                "                <table\n" +
                "                  border=\"0\"\n" +
                "                  cellpadding=\"0\"\n" +
                "                  cellspacing=\"0\"\n" +
                "                  width=\"100%\"\n" +
                "                  style=\"border-collapse: collapse\"\n" +
                "                >\n" +
                "                  <tr>\n" +
                "                    <td\n" +
                "                      style=\"\n" +
                "                        color: #ffffff;\n" +
                "                        font-family: Arial, sans-serif;\n" +
                "                        font-size: 14px;\">\n" +
                "                      <p style=\"margin: 0\">\n" +
                "                        &copy; Arkandas 2020<br />\n" +
                "                      </p>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>\n";
    }
}
