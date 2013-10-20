package ru.denull.wire;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import tl.InputPeerChat;
import tl.InputPeerContact;
import tl.InputPeerForeign;
import tl.InputPeerSelf;
import tl.TInputPeer;
import tl.TUser;

public class Utils {
  public static String[][] countryCodes = {
    { "", "" },
    { "Afghanistan", "93" },
    { "Albania", "355" },
    { "Algeria", "213" },
    { "American Samoa", "1684" },
    { "Andorra", "376" },
    { "Angola", "244" },
    { "Anguilla", "1264" },
    { "Antarctica", "672" },
    { "Antigua and Barbuda", "1268" },
    { "Argentina", "54" },
    { "Armenia", "374" },
    { "Aruba", "297" },
    { "Australia", "61" },
    { "Austria", "43" },
    { "Azerbaijan", "994" },
    { "Bahamas", "1242" },
    { "Bahrain", "973" },
    { "Bangladesh", "880" },
    { "Barbados", "1246" },
    { "Belarus", "375" },
    { "Belgium", "32" },
    { "Belize", "501" },
    { "Benin", "229" },
    { "Bermuda", "1441" },
    { "Bhutan", "975" },
    { "Bolivia", "591" },
    { "Bosnia and Herzegovina", "387" },
    { "Botswana", "267" },
    { "Brazil", "55" },
    { "British Virgin Islands", "1284" },
    { "Brunei", "673" },
    { "Bulgaria", "359" },
    { "Burkina Faso", "226" },
    { "Burma (Myanmar)", "95" },
    { "Burundi", "257" },
    { "Cambodia", "855" },
    { "Cameroon", "237" },
    { "Canada", "1" },
    { "Cape Verde", "238" },
    { "Cayman Islands", "1345" },
    { "Central African Republic", "236" },
    { "Chad", "235" },
    { "Chile", "56" },
    { "China", "86" },
    { "Christmas Island", "61" },
    { "Cocos (Keeling) Islands", "61" },
    { "Colombia", "57" },
    { "Comoros", "269" },
    { "Cook Islands", "682" },
    { "Costa Rica", "506" },
    { "Croatia", "385" },
    { "Cuba", "53" },
    { "Cyprus", "357" },
    { "Czech Republic", "420" },
    { "Democratic Republic of the Congo", "243" },
    { "Denmark", "45" },
    { "Djibouti", "253" },
    { "Dominica", "1767" },
    { "Dominican Republic", "1809" },
    { "Ecuador", "593" },
    { "Egypt", "20" },
    { "El Salvador", "503" },
    { "Equatorial Guinea", "240" },
    { "Eritrea", "291" },
    { "Estonia", "372" },
    { "Ethiopia", "251" },
    { "Falkland Islands", "500" },
    { "Faroe Islands", "298" },
    { "Fiji", "679" },
    { "Finland", "358" },
    { "France", "33" },
    { "French Polynesia", "689" },
    { "Gabon", "241" },
    { "Gambia", "220" },
    { "Gaza Strip", "970" },
    { "Georgia", "995" },
    { "Germany", "49" },
    { "Ghana", "233" },
    { "Gibraltar", "350" },
    { "Greece", "30" },
    { "Greenland", "299" },
    { "Grenada", "1473" },
    { "Guam", "1671" },
    { "Guatemala", "502" },
    { "Guinea", "224" },
    { "Guinea-Bissau", "245" },
    { "Guyana", "592" },
    { "Haiti", "509" },
    { "Holy See (Vatican City)", "39" },
    { "Honduras", "504" },
    { "Hong Kong", "852" },
    { "Hungary", "36" },
    { "Iceland", "354" },
    { "India", "91" },
    { "Indonesia", "62" },
    { "Iran", "98" },
    { "Iraq", "964" },
    { "Ireland", "353" },
    { "Isle of Man", "44" },
    { "Israel", "972" },
    { "Italy", "39" },
    { "Ivory Coast", "225" },
    { "Jamaica", "1876" },
    { "Japan", "81" },
    { "Jordan", "962" },
    { "Kazakhstan", "7" },
    { "Kenya", "254" },
    { "Kiribati", "686" },
    { "Kosovo", "381" },
    { "Kuwait", "965" },
    { "Kyrgyzstan", "996" },
    { "Laos", "856" },
    { "Latvia", "371" },
    { "Lebanon", "961" },
    { "Lesotho", "266" },
    { "Liberia", "231" },
    { "Libya", "218" },
    { "Liechtenstein", "423" },
    { "Lithuania", "370" },
    { "Luxembourg", "352" },
    { "Macau", "853" },
    { "Macedonia", "389" },
    { "Madagascar", "261" },
    { "Malawi", "265" },
    { "Malaysia", "60" },
    { "Maldives", "960" },
    { "Mali", "223" },
    { "Malta", "356" },
    { "Marshall Islands", "692" },
    { "Mauritania", "222" },
    { "Mauritius", "230" },
    { "Mayotte", "262" },
    { "Mexico", "52" },
    { "Micronesia", "691" },
    { "Moldova", "373" },
    { "Monaco", "377" },
    { "Mongolia", "976" },
    { "Montenegro", "382" },
    { "Montserrat", "1664" },
    { "Morocco", "212" },
    { "Mozambique", "258" },
    { "Namibia", "264" },
    { "Nauru", "674" },
    { "Nepal", "977" },
    { "Netherlands", "31" },
    { "Netherlands Antilles", "599" },
    { "New Caledonia", "687" },
    { "New Zealand", "64" },
    { "Nicaragua", "505" },
    { "Niger", "227" },
    { "Nigeria", "234" },
    { "Niue", "683" },
    { "Norfolk Island", "672" },
    { "North Korea", "850" },
    { "Northern Mariana Islands", "1670" },
    { "Norway", "47" },
    { "Oman", "968" },
    { "Pakistan", "92" },
    { "Palau", "680" },
    { "Panama", "507" },
    { "Papua New Guinea", "675" },
    { "Paraguay", "595" },
    { "Peru", "51" },
    { "Philippines", "63" },
    { "Pitcairn Islands", "870" },
    { "Poland", "48" },
    { "Portugal", "351" },
    { "Puerto Rico", "1" },
    { "Qatar", "974" },
    { "Republic of the Congo", "242" },
    { "Romania", "40" },
    { "Russia", "7" },
    { "Rwanda", "250" },
    { "Saint Barthelemy", "590" },
    { "Saint Helena", "290" },
    { "Saint Kitts and Nevis", "1869" },
    { "Saint Lucia", "1758" },
    { "Saint Martin", "1599" },
    { "Saint Pierre and Miquelon", "508" },
    { "Saint Vincent and the Grenadines", "1784" },
    { "Samoa", "685" },
    { "San Marino", "378" },
    { "Sao Tome and Principe", "239" },
    { "Saudi Arabia", "966" },
    { "Senegal", "221" },
    { "Serbia", "381" },
    { "Seychelles", "248" },
    { "Sierra Leone", "232" },
    { "Singapore", "65" },
    { "Slovakia", "421" },
    { "Slovenia", "386" },
    { "Solomon Islands", "677" },
    { "Somalia", "252" },
    { "South Africa", "27" },
    { "South Korea", "82" },
    { "Spain", "34" },
    { "Sri Lanka", "94" },
    { "Sudan", "249" },
    { "Suriname", "597" },
    { "Swaziland", "268" },
    { "Sweden", "46" },
    { "Switzerland", "41" },
    { "Syria", "963" },
    { "Taiwan", "886" },
    { "Tajikistan", "992" },
    { "Tanzania", "255" },
    { "Thailand", "66" },
    { "Timor-Leste", "670" },
    { "Togo", "228" },
    { "Tokelau", "690" },
    { "Tonga", "676" },
    { "Trinidad and Tobago", "1868" },
    { "Tunisia", "216" },
    { "Turkey", "90" },
    { "Turkmenistan", "993" },
    { "Turks and Caicos Islands", "1649" },
    { "Tuvalu", "688" },
    { "Uganda", "256" },
    { "Ukraine", "380" },
    { "United Arab Emirates", "971" },
    { "United Kingdom", "44" },
    { "United States", "1" },
    { "Uruguay", "598" },
    { "US Virgin Islands", "1340" },
    { "Uzbekistan", "998" },
    { "Vanuatu", "678" },
    { "Venezuela", "58" },
    { "Vietnam", "84" },
    { "Wallis and Futuna", "681" },
    { "West Bank", "970" },
    { "Yemen", "967" },
    { "Zambia", "260" },
    { "Zimbabwe", "263" }
  };
  
  public static String[] getCountryNames() {
    String[] result = new String[countryCodes.length];
    for (int i = 0; i < countryCodes.length; i++) {
      result[i] = (i == 0) ? "" : (countryCodes[i][0] + " (+" + countryCodes[i][1] + ")");
    }
    return result;
  }
  
  public static int getPeerID(TInputPeer peer, TUser self) {
    if (peer instanceof InputPeerChat) {
      return -((InputPeerChat) peer).chat_id;
    } else if (peer instanceof InputPeerContact) {
      return ((InputPeerContact) peer).user_id;
    } else if (peer instanceof InputPeerForeign) {
      return ((InputPeerForeign) peer).user_id;
    } else if (peer instanceof InputPeerSelf) {
      return self != null ? self.id : 0;
    }
    return 0;
  }
  
  public static boolean sameDay(int firstDate, int secondDate) {
    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();
    c1.setTimeInMillis(firstDate * 1000L);
    c2.setTimeInMillis(secondDate * 1000L);
    return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR));
  }
  
  public static String toTime(int date) {
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(date * 1000L);
    int minute = c.get(Calendar.MINUTE);
    return c.get(Calendar.HOUR) + ":" + (minute < 10 ? "0" : "") + minute + " " + (c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
  }
  
  static final String[] full_months_eng = {"january", "february", "march", "april", "may", "june", "jule", "august", "september", "october", "november", "december"};
  static final String[] months_eng = {"Jan", "Feb", "Mar", "Apr", "May", "June", "Jule", "Aug", "Sep", "Oct", "Nov", "Dec"};
  public static String toDay(int date) {
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(date * 1000L);
    return c.get(Calendar.DAY_OF_MONTH) + " " + full_months_eng[c.get(Calendar.MONTH)];
  }
  
  public static String toTimeOrDay(int date) {
    if (sameDay(date, (int) (System.currentTimeMillis() / 1000))) {
      return toTime(date);
    } else {
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(date * 1000L);
      return c.get(Calendar.DAY_OF_MONTH) + " " + months_eng[c.get(Calendar.MONTH)];
    }
  }
  
  public static String toDate(int date) {
    return "";// DateFormat.format("dd.MM.yyyy", date * 1000L).toString();
  }
  
  public static String toSize(int size) {
    if (size < 1024) {
      return size + "B";
    } else
    if (size < 1024 * 1024) {
      return String.format("%.1fKB", size / 1024.0f);
    } else
    if (size < 1024 * 1024 * 1024) {
      return String.format("%.1fMB", size / (1024.0f * 1024.0f));
    } else {
      return String.format("%.1fGB", size / (1024.0f * 1024.0f * 1024.0f));
    }
  }
  
  public static String toCount(int count) {
    if (count < 1000) {
      return count + "";
    } else
    if (count < 1000 * 1000) {
      return (count / 1000) + "K";
    } else {
      return (count / 1000000) + "M";
    }
  }
  
  public static String toDuration(int duration) {
    if (duration < 60 * 60) {
      return String.format("%d:%2d", duration / 60, duration % 60);
    } else {
      return String.format("%d:%2d:%2d", duration / 3600, (duration / 60) % 60, duration % 60);
    }
  }
  
  public static Image getImage(String name) {
    return Toolkit.getDefaultToolkit().getImage("NSImage://" + name);
  }
}
