package ru.denull.wire;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.*;

import ru.denull.mtproto.DataService;
import tl.*;

public class Utils {
  public static final String fontName = "Tahoma";
  
  /**
   *  Emoji codes combined into the groups corresponding to the categories in the interface
   *
   **/
  public static long[][] emoji = {
        new long[]{
        0x00000000D83DDE04L, 0x00000000D83DDE03L, 0x00000000D83DDE00L, 0x00000000D83DDE0AL, 0x000000000000263AL, 0x00000000D83DDE09L, 0x00000000D83DDE0DL,
        0x00000000D83DDE18L, 0x00000000D83DDE1AL, 0x00000000D83DDE17L, 0x00000000D83DDE19L, 0x00000000D83DDE1CL, 0x00000000D83DDE1DL, 0x00000000D83DDE1BL,
        0x00000000D83DDE33L, 0x00000000D83DDE01L, 0x00000000D83DDE14L, 0x00000000D83DDE0CL, 0x00000000D83DDE12L, 0x00000000D83DDE1EL, 0x00000000D83DDE23L,
        0x00000000D83DDE22L, 0x00000000D83DDE02L, 0x00000000D83DDE2DL, 0x00000000D83DDE2AL, 0x00000000D83DDE25L, 0x00000000D83DDE30L, 0x00000000D83DDE05L,
        0x00000000D83DDE13L, 0x00000000D83DDE29L, 0x00000000D83DDE2BL, 0x00000000D83DDE28L, 0x00000000D83DDE31L, 0x00000000D83DDE20L, 0x00000000D83DDE21L,
        0x00000000D83DDE24L, 0x00000000D83DDE16L, 0x00000000D83DDE06L, 0x00000000D83DDE0BL, 0x00000000D83DDE37L, 0x00000000D83DDE0EL, 0x00000000D83DDE34L,
        0x00000000D83DDE35L, 0x00000000D83DDE32L, 0x00000000D83DDE1FL, 0x00000000D83DDE26L, 0x00000000D83DDE27L, 0x00000000D83DDE08L, 0x00000000D83DDC7FL,
        0x00000000D83DDE2EL, 0x00000000D83DDE2CL, 0x00000000D83DDE10L, 0x00000000D83DDE15L, 0x00000000D83DDE2FL, 0x00000000D83DDE36L, 0x00000000D83DDE07L,
        0x00000000D83DDE0FL, 0x00000000D83DDE11L, 0x00000000D83DDC72L, 0x00000000D83DDC73L, 0x00000000D83DDC6EL, 0x00000000D83DDC77L, 0x00000000D83DDC82L,
        0x00000000D83DDC76L, 0x00000000D83DDC66L, 0x00000000D83DDC67L, 0x00000000D83DDC68L, 0x00000000D83DDC69L, 0x00000000D83DDC74L, 0x00000000D83DDC75L,
        0x00000000D83DDC71L, 0x00000000D83DDC7CL, 0x00000000D83DDC78L, 0x00000000D83DDE3AL, 0x00000000D83DDE38L, 0x00000000D83DDE3BL, 0x00000000D83DDE3DL,
        0x00000000D83DDE3CL, 0x00000000D83DDE40L, 0x00000000D83DDE3FL, 0x00000000D83DDE39L, 0x00000000D83DDE3EL, 0x00000000D83DDC79L, 0x00000000D83DDC7AL,
        0x00000000D83DDE48L, 0x00000000D83DDE49L, 0x00000000D83DDE4AL, 0x00000000D83DDC80L, 0x00000000D83DDC7DL, 0x00000000D83DDCA9L, 0x00000000D83DDD25L,
        0x0000000000002728L, 0x00000000D83CDF1FL, 0x00000000D83DDCABL, 0x00000000D83DDCA5L, 0x00000000D83DDCA2L, 0x00000000D83DDCA6L, 0x00000000D83DDCA7L,
        0x00000000D83DDCA4L, 0x00000000D83DDCA8L, 0x00000000D83DDC42L, 0x00000000D83DDC40L, 0x00000000D83DDC43L, 0x00000000D83DDC45L, 0x00000000D83DDC44L,
        0x00000000D83DDC4DL, 0x00000000D83DDC4EL, 0x00000000D83DDC4CL, 0x00000000D83DDC4AL, 0x000000000000270AL, 0x000000000000270CL, 0x00000000D83DDC4BL,
        0x000000000000270BL, 0x00000000D83DDC50L, 0x00000000D83DDC46L, 0x00000000D83DDC47L, 0x00000000D83DDC49L, 0x00000000D83DDC48L, 0x00000000D83DDE4CL,
        0x00000000D83DDE4FL, 0x000000000000261DL, 0x00000000D83DDC4FL, 0x00000000D83DDCAAL, 0x00000000D83DDEB6L, 0x00000000D83CDFC3L, 0x00000000D83DDC83L,
        0x00000000D83DDC6BL, 0x00000000D83DDC6AL, 0x00000000D83DDC6CL, 0x00000000D83DDC6DL, 0x00000000D83DDC8FL, 0x00000000D83DDC91L, 0x00000000D83DDC6FL,
        0x00000000D83DDE46L, 0x00000000D83DDE45L, 0x00000000D83DDC81L, 0x00000000D83DDE4BL, 0x00000000D83DDC86L, 0x00000000D83DDC87L, 0x00000000D83DDC85L,
        0x00000000D83DDC70L, 0x00000000D83DDE4EL, 0x00000000D83DDE4DL, 0x00000000D83DDE47L, 0x00000000D83CDFA9L, 0x00000000D83DDC51L, 0x00000000D83DDC52L,
        0x00000000D83DDC5FL, 0x00000000D83DDC5EL, 0x00000000D83DDC61L, 0x00000000D83DDC60L, 0x00000000D83DDC62L, 0x00000000D83DDC55L, 0x00000000D83DDC54L,
        0x00000000D83DDC5AL, 0x00000000D83DDC57L, 0x00000000D83CDFBDL, 0x00000000D83DDC56L, 0x00000000D83DDC58L, 0x00000000D83DDC59L, 0x00000000D83DDCBCL,
        0x00000000D83DDC5CL, 0x00000000D83DDC5DL, 0x00000000D83DDC5BL, 0x00000000D83DDC53L, 0x00000000D83CDF80L, 0x00000000D83CDF02L, 0x00000000D83DDC84L,
        0x00000000D83DDC9BL, 0x00000000D83DDC99L, 0x00000000D83DDC9CL, 0x00000000D83DDC9AL, 0x0000000000002764L, 0x00000000D83DDC94L, 0x00000000D83DDC97L,
        0x00000000D83DDC93L, 0x00000000D83DDC95L, 0x00000000D83DDC96L, 0x00000000D83DDC9EL, 0x00000000D83DDC98L, 0x00000000D83DDC8CL, 0x00000000D83DDC8BL,
        0x00000000D83DDC8DL, 0x00000000D83DDC8EL, 0x00000000D83DDC64L, 0x00000000D83DDC65L, 0x00000000D83DDCACL, 0x00000000D83DDC63L, 0x00000000D83DDCADL},
        new long[]{
        0x00000000D83DDC36L, 0x00000000D83DDC3AL, 0x00000000D83DDC31L, 0x00000000D83DDC2DL, 0x00000000D83DDC39L, 0x00000000D83DDC30L, 0x00000000D83DDC38L, 0x00000000D83DDC2FL,
        0x00000000D83DDC28L, 0x00000000D83DDC3BL, 0x00000000D83DDC37L, 0x00000000D83DDC3DL, 0x00000000D83DDC2EL, 0x00000000D83DDC17L, 0x00000000D83DDC35L,
        0x00000000D83DDC12L, 0x00000000D83DDC34L, 0x00000000D83DDC11L, 0x00000000D83DDC18L, 0x00000000D83DDC3CL, 0x00000000D83DDC27L, 0x00000000D83DDC26L,
        0x00000000D83DDC24L, 0x00000000D83DDC25L, 0x00000000D83DDC23L, 0x00000000D83DDC14L, 0x00000000D83DDC0DL, 0x00000000D83DDC22L, 0x00000000D83DDC1BL,
        0x00000000D83DDC1DL, 0x00000000D83DDC1CL, 0x00000000D83DDC1EL, 0x00000000D83DDC0CL, 0x00000000D83DDC19L, 0x00000000D83DDC1AL, 0x00000000D83DDC20L,
        0x00000000D83DDC1FL, 0x00000000D83DDC2CL, 0x00000000D83DDC33L, 0x00000000D83DDC0BL, 0x00000000D83DDC04L, 0x00000000D83DDC0FL, 0x00000000D83DDC00L,
        0x00000000D83DDC03L, 0x00000000D83DDC05L, 0x00000000D83DDC07L, 0x00000000D83DDC09L, 0x00000000D83DDC0EL, 0x00000000D83DDC10L, 0x00000000D83DDC13L,
        0x00000000D83DDC15L, 0x00000000D83DDC16L, 0x00000000D83DDC01L, 0x00000000D83DDC02L, 0x00000000D83DDC32L, 0x00000000D83DDC21L, 0x00000000D83DDC0AL,
        0x00000000D83DDC2BL, 0x00000000D83DDC2AL, 0x00000000D83DDC06L, 0x00000000D83DDC08L, 0x00000000D83DDC29L, 0x00000000D83DDC3EL, 0x00000000D83DDC90L,
        0x00000000D83CDF38L, 0x00000000D83CDF37L, 0x00000000D83CDF40L, 0x00000000D83CDF39L, 0x00000000D83CDF3BL, 0x00000000D83CDF3AL, 0x00000000D83CDF41L,
        0x00000000D83CDF43L, 0x00000000D83CDF42L, 0x00000000D83CDF3FL, 0x00000000D83CDF3EL, 0x00000000D83CDF44L, 0x00000000D83CDF35L, 0x00000000D83CDF34L,
        0x00000000D83CDF32L, 0x00000000D83CDF33L, 0x00000000D83CDF30L, 0x00000000D83CDF31L, 0x00000000D83CDF3CL, 0x00000000D83CDF10L, 0x00000000D83CDF1EL,
        0x00000000D83CDF1DL, 0x00000000D83CDF1AL, 0x00000000D83CDF11L, 0x00000000D83CDF12L, 0x00000000D83CDF13L, 0x00000000D83CDF14L, 0x00000000D83CDF15L,
        0x00000000D83CDF16L, 0x00000000D83CDF17L, 0x00000000D83CDF18L, 0x00000000D83CDF1CL, 0x00000000D83CDF1BL, 0x00000000D83CDF19L, 0x00000000D83CDF0DL,
        0x00000000D83CDF0EL, 0x00000000D83CDF0FL, 0x00000000D83CDF0BL, 0x00000000D83CDF0CL, 0x00000000D83CDF20L, 0x0000000000002B50L, 0x0000000000002600L,
        0x00000000000026C5L, 0x0000000000002601L, 0x00000000000026A1L, 0x0000000000002614L, 0x0000000000002744L, 0x00000000000026C4L, 0x00000000D83CDF00L,
        0x00000000D83CDF01L, 0x00000000D83CDF08L, 0x00000000D83CDF0AL},
        new long[] {
        0x00000000D83CDF8DL, 0x00000000D83DDC9DL, 0x00000000D83CDF8EL, 0x00000000D83CDF92L, 0x00000000D83CDF93L, 0x00000000D83CDF8FL, 0x00000000D83CDF86L, 0x00000000D83CDF87L,
        0x00000000D83CDF90L, 0x00000000D83CDF91L, 0x00000000D83CDF83L, 0x00000000D83DDC7BL, 0x00000000D83CDF85L, 0x00000000D83CDF84L, 0x00000000D83CDF81L,
        0x00000000D83CDF8BL, 0x00000000D83CDF89L, 0x00000000D83CDF8AL, 0x00000000D83CDF88L, 0x00000000D83CDF8CL, 0x00000000D83DDD2EL, 0x00000000D83CDFA5L,
        0x00000000D83DDCF7L, 0x00000000D83DDCF9L, 0x00000000D83DDCFCL, 0x00000000D83DDCBFL, 0x00000000D83DDCC0L, 0x00000000D83DDCBDL, 0x00000000D83DDCBEL,
        0x00000000D83DDCBBL, 0x00000000D83DDCF1L, 0x000000000000260EL, 0x00000000D83DDCDEL, 0x00000000D83DDCDFL, 0x00000000D83DDCE0L, 0x00000000D83DDCE1L,
        0x00000000D83DDCFAL, 0x00000000D83DDCFBL, 0x00000000D83DDD0AL, 0x00000000D83DDD09L, 0x00000000D83DDD08L, 0x00000000D83DDD07L, 0x00000000D83DDD14L,
        0x00000000D83DDD14L, 0x00000000D83DDCE2L, 0x00000000D83DDCE3L, 0x00000000000023F3L, 0x000000000000231BL, 0x00000000000023F0L, 0x000000000000231AL,
        0x00000000D83DDD13L, 0x00000000D83DDD12L, 0x00000000D83DDD0FL, 0x00000000D83DDD10L, 0x00000000D83DDD11L, 0x00000000D83DDD0EL, 0x00000000D83DDCA1L,
        0x00000000D83DDD26L, 0x00000000D83DDD06L, 0x00000000D83DDD05L, 0x00000000D83DDD0CL, 0x00000000D83DDD0BL, 0x00000000D83DDD0DL, 0x00000000D83DDEC0L,
        0x00000000D83DDEBFL, 0x00000000D83DDEBDL, 0x00000000D83DDD27L, 0x00000000D83DDD29L, 0x00000000D83DDD28L, 0x00000000D83DDEAAL, 0x00000000D83DDEACL,
        0x00000000D83DDCA3L, 0x00000000D83DDD2BL, 0x00000000D83DDD2AL, 0x00000000D83DDC8AL, 0x00000000D83DDC89L, 0x00000000D83DDCB0L, 0x00000000D83DDCB4L,
        0x00000000D83DDCB5L, 0x00000000D83DDCB7L, 0x00000000D83DDCB6L, 0x00000000D83DDCB3L, 0x00000000D83DDCB8L, 0x00000000D83DDCF2L, 0x00000000D83DDCE7L,
        0x00000000D83DDCE5L, 0x00000000D83DDCE4L, 0x0000000000002709L, 0x00000000D83DDCE9L, 0x00000000D83DDCE8L, 0x00000000D83DDCEFL, 0x00000000D83DDCEBL,
        0x00000000D83DDCEAL, 0x00000000D83DDCECL, 0x00000000D83DDCEDL, 0x00000000D83DDCEEL, 0x00000000D83DDCE6L, 0x00000000D83DDCDDL, 0x00000000D83DDCC4L,
        0x00000000D83DDCC3L, 0x00000000D83DDCD1L, 0x00000000D83DDCCAL, 0x00000000D83DDCC8L, 0x00000000D83DDCC9L, 0x00000000D83DDCDCL, 0x00000000D83DDCCBL,
        0x00000000D83DDCC5L, 0x00000000D83DDCC6L, 0x00000000D83DDCC7L, 0x00000000D83DDCC1L, 0x00000000D83DDCC2L, 0x0000000000002702L, 0x00000000D83DDCCCL,
        0x00000000D83DDCCEL, 0x0000000000002712L, 0x000000000000270FL, 0x00000000D83DDCCFL, 0x00000000D83DDCD0L, 0x00000000D83DDCD5L, 0x00000000D83DDCD7L,
        0x00000000D83DDCD8L, 0x00000000D83DDCD9L, 0x00000000D83DDCD3L, 0x00000000D83DDCD4L, 0x00000000D83DDCD2L, 0x00000000D83DDCDAL, 0x00000000D83DDCD6L,
        0x00000000D83DDD16L, 0x00000000D83DDCDBL, 0x00000000D83DDD2CL, 0x00000000D83DDD2DL, 0x00000000D83DDCF0L, 0x00000000D83CDFA8L, 0x00000000D83CDFACL,
        0x00000000D83CDFA4L, 0x00000000D83CDFA7L, 0x00000000D83CDFBCL, 0x00000000D83CDFB5L, 0x00000000D83CDFB6L, 0x00000000D83CDFB9L, 0x00000000D83CDFBBL,
        0x00000000D83CDFBAL, 0x00000000D83CDFB7L, 0x00000000D83CDFB8L, 0x00000000D83DDC7EL, 0x00000000D83CDFAEL, 0x00000000D83CDCCFL, 0x00000000D83CDFB4L,
        0x00000000D83CDC04L, 0x00000000D83CDFB2L, 0x00000000D83CDFAFL, 0x00000000D83CDFC8L, 0x00000000D83CDFC0L, 0x00000000000026BDL, 0x00000000000026BEL,
        0x00000000D83CDFBEL, 0x00000000D83CDFB1L, 0x00000000D83CDFC9L, 0x00000000D83CDFB3L, 0x00000000000026F3L, 0x00000000D83DDEB5L, 0x00000000D83DDEB4L,
        0x00000000D83CDFC1L, 0x00000000D83CDFC7L, 0x00000000D83CDFC6L, 0x00000000D83CDFBFL, 0x00000000D83CDFC2L, 0x00000000D83CDFCAL, 0x00000000D83CDFC4L,
        0x00000000D83CDFA3L, 0x0000000000002615L, 0x00000000D83CDF75L, 0x00000000D83CDF76L, 0x00000000D83CDF7CL, 0x00000000D83CDF7AL, 0x00000000D83CDF7BL,
        0x00000000D83CDF78L, 0x00000000D83CDF79L, 0x00000000D83CDF77L, 0x00000000D83CDF74L, 0x00000000D83CDF55L, 0x00000000D83CDF54L, 0x00000000D83CDF5FL,
        0x00000000D83CDF57L, 0x00000000D83CDF56L, 0x00000000D83CDF5DL, 0x00000000D83CDF5BL, 0x00000000D83CDF64L, 0x00000000D83CDF71L, 0x00000000D83CDF63L,
        0x00000000D83CDF65L, 0x00000000D83CDF59L, 0x00000000D83CDF58L, 0x00000000D83CDF5AL, 0x00000000D83CDF5CL, 0x00000000D83CDF72L, 0x00000000D83CDF62L,
        0x00000000D83CDF61L, 0x00000000D83CDF73L, 0x00000000D83CDF5EL, 0x00000000D83CDF69L, 0x00000000D83CDF6EL, 0x00000000D83CDF66L, 0x00000000D83CDF68L,
        0x00000000D83CDF67L, 0x00000000D83CDF82L, 0x00000000D83CDF70L, 0x00000000D83CDF6AL, 0x00000000D83CDF6BL, 0x00000000D83CDF6CL, 0x00000000D83CDF6DL,
        0x00000000D83CDF6FL, 0x00000000D83CDF4EL, 0x00000000D83CDF4FL, 0x00000000D83CDF4AL, 0x00000000D83CDF4BL, 0x00000000D83CDF52L, 0x00000000D83CDF47L,
        0x00000000D83CDF49L, 0x00000000D83CDF53L, 0x00000000D83CDF51L, 0x00000000D83CDF48L, 0x00000000D83CDF4CL, 0x00000000D83CDF50L, 0x00000000D83CDF4DL,
        0x00000000D83CDF60L, 0x00000000D83CDF46L, 0x00000000D83CDF45L, 0x00000000D83CDF3DL},
        new long[]{
        0x00000000D83CDFE0L, 0x00000000D83CDFE1L, 0x00000000D83CDFEBL, 0x00000000D83CDFE2L, 0x00000000D83CDFE3L, 0x00000000D83CDFE5L, 0x00000000D83CDFE6L, 0x00000000D83CDFEAL,
        0x00000000D83CDFE9L, 0x00000000D83CDFE8L, 0x00000000D83DDC92L, 0x00000000000026EAL, 0x00000000D83CDFECL, 0x00000000D83CDFE4L, 0x00000000D83CDF07L,
        0x00000000D83CDF06L, 0x00000000D83CDFEFL, 0x00000000D83CDFF0L, 0x00000000000026FAL, 0x00000000D83CDFEDL, 0x00000000D83DDDFCL, 0x00000000D83DDDFEL,
        0x00000000D83DDDFBL, 0x00000000D83CDF04L, 0x00000000D83CDF05L, 0x00000000D83CDF03L, 0x00000000D83DDDFDL, 0x00000000D83CDF09L, 0x00000000D83CDFA0L,
        0x00000000D83CDFA1L, 0x00000000000026F2L, 0x00000000D83CDFA2L, 0x00000000D83DDEA2L, 0x00000000000026F5L, 0x00000000D83DDEA4L, 0x00000000D83DDEA3L,
        0x0000000000002693L, 0x00000000D83DDE80L, 0x0000000000002708L, 0x00000000D83DDCBAL, 0x00000000D83DDE81L, 0x00000000D83DDE82L, 0x00000000D83DDE8AL,
        0x00000000D83DDE89L, 0x00000000D83DDE9EL, 0x00000000D83DDE86L, 0x00000000D83DDE84L, 0x00000000D83DDE85L, 0x00000000D83DDE88L, 0x00000000D83DDE87L,
        0x00000000D83DDE9DL, 0x00000000D83DDE8BL, 0x00000000D83DDE83L, 0x00000000D83DDE8EL, 0x00000000D83DDE8CL, 0x00000000D83DDE8DL, 0x00000000D83DDE99L,
        0x00000000D83DDE98L, 0x00000000D83DDE97L, 0x00000000D83DDE95L, 0x00000000D83DDE96L, 0x00000000D83DDE9BL, 0x00000000D83DDE9AL, 0x00000000D83DDEA8L,
        0x00000000D83DDE93L, 0x00000000D83DDE94L, 0x00000000D83DDE92L, 0x00000000D83DDE91L, 0x00000000D83DDE90L, 0x00000000D83DDEB2L, 0x00000000D83DDEA1L,
        0x00000000D83DDE9FL, 0x00000000D83DDEA0L, 0x00000000D83DDE9CL, 0x00000000D83DDC88L, 0x00000000D83DDE8FL, 0x00000000D83CDFABL, 0x00000000D83DDEA6L,
        0x00000000D83DDEA5L, 0x00000000000026A0L, 0x00000000D83DDEA7L, 0x00000000D83DDD30L, 0x00000000000026FDL, 0x00000000D83CDFEEL, 0x00000000D83CDFB0L,
        0x0000000000002668L, 0x00000000D83DDDFFL, 0x00000000D83CDFAAL, 0x00000000D83CDFADL, 0x00000000D83DDCCDL, 0x00000000D83DDEA9L, 0xD83CDDEFD83CDDF5L,
        0xD83CDDF0D83CDDF7L, 0xD83CDDE9D83CDDEAL, 0xD83CDDE8D83CDDF3L, 0xD83CDDFAD83CDDF8L, 0xD83CDDEBD83CDDF7L, 0xD83CDDEAD83CDDF8L, 0xD83CDDEED83CDDF9L,
        0xD83CDDF7D83CDDFAL, 0xD83CDDECD83CDDE7L},
        new long[]{
        0x00000000003120E3L, 0x00000000003220E3L, 0x00000000003320E3L, 0x00000000003420E3L, 0x00000000003520E3L, 0x00000000003620E3L, 0x00000000003720E3L, 0x00000000003820E3L,
        0x00000000003920E3L, 0x00000000003020E3L, 0x00000000D83DDD1FL, 0x00000000D83DDD22L, 0x00000000002320E3L, 0x00000000D83DDD23L, 0x0000000000002B06L,
        0x0000000000002B07L, 0x0000000000002B05L, 0x00000000000027A1L, 0x00000000D83DDD20L, 0x00000000D83DDD21L, 0x00000000D83DDD24L, 0x0000000000002197L,
        0x0000000000002196L, 0x0000000000002198L, 0x0000000000002199L, 0x0000000000002194L, 0x0000000000002195L, 0x00000000D83DDD04L, 0x00000000000025C0L,
        0x00000000000025B6L, 0x00000000D83DDD3CL, 0x00000000D83DDD3DL, 0x00000000000021A9L, 0x00000000000021AAL, 0x0000000000002139L, 0x00000000000023EAL,
        0x00000000000023E9L, 0x00000000000023EBL, 0x00000000000023ECL, 0x0000000000002935L, 0x0000000000002934L, 0x00000000D83CDD97L, 0x00000000D83DDD00L,
        0x00000000D83DDD01L, 0x00000000D83DDD02L, 0x00000000D83CDD95L, 0x00000000D83CDD99L, 0x00000000D83CDD92L, 0x00000000D83CDD93L, 0x00000000D83CDD96L,
        0x00000000D83DDCF6L, 0x00000000D83CDFA6L, 0x00000000D83CDE01L, 0x00000000D83CDE2FL, 0x00000000D83CDE33L, 0x00000000D83CDE35L, 0x00000000D83CDE32L,
        0x00000000D83CDE34L, 0x00000000D83CDE32L, 0x00000000D83CDE50L, 0x00000000D83CDE39L, 0x00000000D83CDE3AL, 0x00000000D83CDE36L, 0x00000000D83CDE1AL,
        0x00000000D83DDEBBL, 0x00000000D83DDEB9L, 0x00000000D83DDEBAL, 0x00000000D83DDEBCL, 0x00000000D83DDEBEL, 0x00000000D83DDEB0L, 0x00000000D83DDEAEL,
        0x00000000D83CDD7FL, 0x000000000000267FL, 0x00000000D83DDEADL, 0x00000000D83CDE37L, 0x00000000D83CDE38L, 0x00000000D83CDE02L, 0x00000000000024C2L,
        0x00000000D83CDE51L, 0x0000000000003299L, 0x0000000000003297L, 0x00000000D83CDD91L, 0x00000000D83CDD98L, 0x00000000D83CDD94L, 0x00000000D83DDEABL,
        0x00000000D83DDD1EL, 0x00000000D83DDCF5L, 0x00000000D83DDEAFL, 0x00000000D83DDEB1L, 0x00000000D83DDEB3L, 0x00000000D83DDEB7L, 0x00000000D83DDEB8L,
        0x00000000000026D4L, 0x0000000000002733L, 0x0000000000002747L, 0x000000000000274EL, 0x0000000000002705L, 0x0000000000002734L, 0x00000000D83DDC9FL,
        0x00000000D83CDD9AL, 0x00000000D83DDCF3L, 0x00000000D83DDCF4L, 0x00000000D83CDD70L, 0x00000000D83CDD71L, 0x00000000D83CDD8EL, 0x00000000D83CDD7EL,
        0x00000000D83DDCA0L, 0x00000000000027BFL, 0x000000000000267BL, 0x0000000000002648L, 0x0000000000002649L, 0x000000000000264AL, 0x000000000000264BL,
        0x000000000000264CL, 0x000000000000264DL, 0x000000000000264EL, 0x000000000000264FL, 0x0000000000002650L, 0x0000000000002651L, 0x0000000000002652L,
        0x0000000000002653L, 0x00000000000026CEL, 0x00000000D83DDD2FL, 0x00000000D83CDFE7L, 0x00000000D83DDCB9L, 0x00000000D83DDCB2L, 0x00000000D83DDCB1L,
        0x00000000000000A9L, 0x00000000000000AEL, 0x0000000000002122L, 0x000000000000303DL, 0x0000000000003030L, 0x00000000D83DDD1DL, 0x00000000D83DDD1AL,
        0x00000000D83DDD19L, 0x00000000D83DDD1BL, 0x00000000D83DDD1CL, 0x000000000000274CL, 0x0000000000002B55L, 0x0000000000002757L, 0x0000000000002753L,
        0x0000000000002755L, 0x0000000000002754L, 0x00000000D83DDD03L, 0x00000000D83DDD5BL, 0x00000000D83DDD67L, 0x00000000D83DDD50L, 0x00000000D83DDD5CL,
        0x00000000D83DDD51L, 0x00000000D83DDD5DL, 0x00000000D83DDD52L, 0x00000000D83DDD5EL, 0x00000000D83DDD53L, 0x00000000D83DDD5FL, 0x00000000D83DDD54L,
        0x00000000D83DDD60L, 0x00000000D83DDD55L, 0x00000000D83DDD56L, 0x00000000D83DDD57L, 0x00000000D83DDD58L, 0x00000000D83DDD59L, 0x00000000D83DDD5AL,
        0x00000000D83DDD61L, 0x00000000D83DDD62L, 0x00000000D83DDD63L, 0x00000000D83DDD64L, 0x00000000D83DDD65L, 0x00000000D83DDD66L, 0x0000000000002716L,
        0x0000000000002795L, 0x0000000000002796L, 0x0000000000002797L, 0x0000000000002660L, 0x0000000000002665L, 0x0000000000002663L, 0x0000000000002666L,
        0x00000000D83DDCAEL, 0x00000000D83DDCAFL, 0x0000000000002714L, 0x0000000000002611L, 0x00000000D83DDD18L, 0x00000000D83DDD17L, 0x00000000000027B0L,
        0x00000000D83DDD31L, 0x00000000D83DDD32L, 0x00000000D83DDD33L, 0x00000000000025FCL, 0x00000000000025FBL, 0x00000000000025FEL, 0x00000000000025FDL,
        0x00000000000025AAL, 0x00000000000025ABL, 0x00000000D83DDD3AL, 0x0000000000002B1CL, 0x0000000000002B1BL, 0x00000000000026ABL, 0x00000000000026AAL,
        0x00000000D83DDD34L, 0x00000000D83DDD35L, 0x00000000D83DDD3BL, 0x00000000D83DDD36L, 0x00000000D83DDD37L, 0x00000000D83DDD38L, 0x00000000D83DDD39L}};
  public static HashMap<Long, Integer> emoji_pos = new HashMap<Long, Integer>();
  static { // fill emoji info
    for (int page = 0; page < emoji.length; page++) {
      for (int index = 0; index < emoji[page].length; index++) {
        long code = emoji[page][index];
        emoji_pos.put(code, (page << 16) | index);
      }
    }
  }
  
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
    //return c.get(Calendar.HOUR) + ":" + (minute < 10 ? "0" : "") + minute + " " + (c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
    return c.get(Calendar.HOUR_OF_DAY) + ":" + (minute < 10 ? "0" : "") + minute;
  }
  
  static final String[] full_months_eng = {"january", "february", "march", "april", "may", "june", "jule", "august", "september", "october", "november", "december"};
  static final String[] months_eng = {"Jan", "Feb", "Mar", "Apr", "May", "June", "Jule", "Aug", "Sep", "Oct", "Nov", "Dec"};
  static final String[] full_months_rus = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
  static final String[] months_rus = {"янв", "фев", "мар", "апр", "мая", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
  public static String toDay(int date) {
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(date * 1000L);
    return c.get(Calendar.DAY_OF_MONTH) + " " + full_months_rus[c.get(Calendar.MONTH)];
  }
  
  public static String toTimeOrDay(int date) {
    if (sameDay(date, (int) (System.currentTimeMillis() / 1000))) {
      return toTime(date);
    } else {
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(date * 1000L);
      return c.get(Calendar.DAY_OF_MONTH) + " " + months_rus[c.get(Calendar.MONTH)];
    }
  }
  
  public static String toDate(int date) {
    return new SimpleDateFormat("dd.MM.yyyy").format(new Date(date * 1000L));
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
  
  /*
   * Eng
   *
  public static String getServiceMessageDesc(DataService service, TMessage message) {
    TUser user = service.userManager.get(message.from_id);
    TMessageAction action = ((MessageService) message).action;
    String actionDesc = "did something";
    if (action instanceof MessageActionChatCreate) {
      actionDesc = "created chat";
    } else if (action instanceof MessageActionChatEditTitle) {
      actionDesc = "changed chat title";
    } else if (action instanceof MessageActionChatEditPhoto) {
      actionDesc = "changed chat photo";
    } else if (action instanceof MessageActionChatDeletePhoto) {
      actionDesc = "removed chat photo";
    } else if (action instanceof MessageActionChatAddUser) {
      TUser mentioned = service.userManager.get(((MessageActionChatAddUser) action).user_id);
      boolean self = (user.id == mentioned.id);
      actionDesc = "invited " + (mentioned instanceof UserSelf ? (self ? "yourself" : "you") : (self ? "himself" : (mentioned.first_name + " " + mentioned.last_name)));
    } else if (action instanceof MessageActionChatDeleteUser) {
      TUser mentioned = service.userManager.get(((MessageActionChatDeleteUser) action).user_id);
      boolean self = (user.id == mentioned.id);
      actionDesc = "kicked " + (mentioned instanceof UserSelf ? (self ? "yourself" : "you") : (self ? "himself" : (mentioned.first_name + " " + mentioned.last_name)));
    }
    return (user instanceof UserEmpty) ? "" : (user instanceof UserSelf ? "You" : (user.first_name + " " + user.last_name)) + " " + actionDesc;
  }
  */
  public static String getServiceMessageDesc(DataService service, TMessage message) {
    TUser user = service.userManager.get(message.from_id);
    TMessageAction action = ((MessageService) message).action;
    
    String ending = (user instanceof UserSelf ? "и" : "");//"(а)"
    
    String actionDesc = "";
    if (action instanceof MessageActionChatCreate) {
      actionDesc = "создал" + ending + " чат";
    } else if (action instanceof MessageActionChatEditTitle) {
      actionDesc = "изменил" + ending + " название чата";
    } else if (action instanceof MessageActionChatEditPhoto) {
      actionDesc = "изменил" + ending + " фото чата";
    } else if (action instanceof MessageActionChatDeletePhoto) {
      actionDesc = "удалил" + ending + " фото чата";
    } else if (action instanceof MessageActionChatAddUser) {
      TUser mentioned = service.userManager.get(((MessageActionChatAddUser) action).user_id);
      boolean self = (user.id == mentioned.id);
      actionDesc = "пригласил" + ending + " " + (mentioned instanceof UserSelf ? (self ? "себя" : "вас") : (self ? "себя" : (mentioned.first_name + " " + mentioned.last_name)));
    } else if (action instanceof MessageActionChatDeleteUser) {
      TUser mentioned = service.userManager.get(((MessageActionChatDeleteUser) action).user_id);
      boolean self = (user.id == mentioned.id);
      if (self) {
        actionDesc = (user instanceof UserSelf ? "вышли" : "вышел") + " из чата";
      } else {
        actionDesc = "исключил" + ending + " " + (mentioned instanceof UserSelf ? "вас" : (mentioned.first_name + " " + mentioned.last_name)) + " из чата";
      }
    }
    return (user instanceof UserEmpty) ? "" : (user instanceof UserSelf ? "Вы" : (user.first_name + " " + user.last_name)) + " " + actionDesc;
  }
  public static final String[] userColors = {"ee4928", "41a903", "e09602", "0f94ed", "8f3bf7", "fc4380", "00a1c4", "eb7002"};
  
  public static String parseEmoji(String text) {
    return parseEmoji(text, null, false);
  }
  public static String parseEmoji(String text, TUser user, boolean whitespan) {
    return parseEmoji(text, user, null, whitespan);
  }
  public static String parseEmoji(String text, TUser user, TUser forward, boolean whitespan) {
    text = text.replaceAll("\n", "<br>");
    
    if (forward != null) {
      //text = "<font color='#006fc8'>Forwarded Message<br>From " + forward.first_name + " " + forward.last_name + "</font><br>" + text;
      text = "<font color='#006fc8'>Пересланное сообщение<br>" + forward.first_name + " " + forward.last_name + "</font><br>" + text;
    }
    if (user != null) {
      text = "<font color='#" + userColors[user.id & 7] + "'>" + user.first_name + " " + user.last_name + "</font><br>" + text;
    }

    return "<html>" + text + "</html>";
  }
  
  public static String toStatus(TUserStatus status, boolean full) {
    if (status instanceof UserStatusOnline) { // TODO: somehow set timer to update status when it expires
      return "<html><font color='#006fc8'>в сети</font></html>";
    } else
    if (status instanceof UserStatusOffline){
      int was_online = ((UserStatusOffline) status).was_online;
      if (was_online == 0) {
        return "невидимый";
      } else
      if (sameDay(was_online, (int) (System.currentTimeMillis() / 1000))) {
        return "заходил" + (full ? " сегодня" : "") + " в " + toTime(was_online);
      } else {
        return "заходил " + toDate(was_online) + (full ? " в " + toTime(was_online) : "");
      }
    }
    return "не в сети";
  }
  
  public static void fixEmoji(JLabel label) {
    View v = (View) label.getClientProperty("html");
    if (v == null || !(v.getDocument() instanceof StyledDocument)) {
      return;
    }
    
    StyledDocument doc = (StyledDocument) v.getDocument();
    
    int len = doc.getLength();
    String text = "";
    try {
      text = doc.getText(0, len);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
    
    for (int i = 0; i < len; i++) {
      long code = text.charAt(i);
      
      Icon icon = null;
      int skip = 0;
      if (i < len - 1 && (code == 0xD83D || code == 0xD83C || code == 35 || (code >= 48 && code <= 57))) { // 4-byte emoji
        code = (code << 16) | text.charAt(i + 1);
        if (i < len - 3 &&
            code == 0xD83CDDEF || code == 0xD83CDDF0 || code == 0xD83CDDE9 || code == 0xD83CDDE8 ||
            code == 0xD83CDDFA || code == 0xD83CDDEB || code == 0xD83CDDEA || code == 0xD83CDDEE ||
            code == 0xD83CDDF7 || code == 0xD83CDDEC) { // 8-byte emoji
          code = /*(code << 32) |*/ (text.charAt(i + 2) << 16) | text.charAt(i + 3);
          
          icon = getEmojiIcon(code);
          skip = 3;
        } else {
          icon = getEmojiIcon(code);
          skip = 1;
        }
      } else
      if (code == 0x00A9 || code == 0x00AE || code > 0x2100) { // 2-byte emoji
        icon = getEmojiIcon(code);
      }
      
      if (icon != null) {
        //str.setSpan(new EmojiSpan(c, pos), i, i + skip + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Element el = doc.getCharacterElement(i);
        if (StyleConstants.getIcon(el.getAttributes()) == null) {
          /*try {
            doc.remove(i, skip + 1);
          } catch (BadLocationException e) {
            e.printStackTrace();
          }*/
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setIcon(attrs, icon);
            doc.setCharacterAttributes(i, skip + 1, attrs, true);
            /*try {
              doc.insertString(i, "1", attrs);
            } catch (BadLocationException e) {
              e.printStackTrace();
            }*/
        }
        
        i += skip;
        //break;
      }
    }
    label.invalidate();
  }
  public static void fixEmoji(JTextPane pane) {
    StyledDocument doc = pane.getStyledDocument();
    
    int len = doc.getLength();
    String text = "";
    try {
      text = doc.getText(0, len);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
    
    for (int i = 0; i < len; i++) {
      long code = text.charAt(i);
      
      Icon icon = null;
      int skip = 0;
      if (i < len - 1 && (code == 0xD83D || code == 0xD83C || code == 35 || (code >= 48 && code <= 57))) { // 4-byte emoji
        code = (code << 16) | text.charAt(i + 1);
        if (i < len - 3 &&
            code == 0xD83CDDEF || code == 0xD83CDDF0 || code == 0xD83CDDE9 || code == 0xD83CDDE8 ||
            code == 0xD83CDDFA || code == 0xD83CDDEB || code == 0xD83CDDEA || code == 0xD83CDDEE ||
            code == 0xD83CDDF7 || code == 0xD83CDDEC) { // 8-byte emoji
          code = /*(code << 32) |*/ (text.charAt(i + 2) << 16) | text.charAt(i + 3);
          
          icon = getEmojiIcon(code);
          skip = 3;
        } else {
          icon = getEmojiIcon(code);
          skip = 1;
        }
      } else
      if (code == 0x00A9 || code == 0x00AE || code > 0x2100) { // 2-byte emoji
        icon = getEmojiIcon(code);
      }
      
      if (icon != null) {
        //str.setSpan(new EmojiSpan(c, pos), i, i + skip + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Element el = doc.getCharacterElement(i);
        if (StyleConstants.getIcon(el.getAttributes()) == null) {
          try {
            doc.remove(i, skip + 1);
          } catch (BadLocationException e) {
            e.printStackTrace();
          }
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setIcon(attrs, icon);
            //doc.setCharacterAttributes(i, skip, attrs, true);
            try {
              doc.insertString(i, "1", attrs);
            } catch (BadLocationException e) {
              e.printStackTrace();
            }
        }
        
        i += skip;
        //break;
      }
    }
  }
  
  public static Image[] emojiSprites = new Image[5];
  public static HashMap<Long, Icon> emojiIcons = new HashMap<Long, Icon>();
  public static Icon getEmojiIcon(int page, int index) {
    int cols = 1, rows = 1, col, row;
    switch (page) {
      case 0: cols = 27; rows = 7; break;
      case 1: cols = 29; rows = 4; break;
      case 2: cols = 33; rows = 7; break;
      case 3: cols = 34; rows = 3; break;
      case 4: cols = 34; rows = 6; break;
    }
    
    col = (index % cols);
    row = (index / cols);
    
    if (emojiSprites[page] == null) {
      emojiSprites[page] = getImage("emojisprite_" + page + ".png");
    }
    
    Image sprite = emojiSprites[page];
    int spriteWidth = sprite.getWidth(null) / cols;
    int spriteHeight = sprite.getHeight(null) / rows;
    
    return new EmojiIcon(sprite, spriteWidth, spriteHeight, col, row);
  }
  
  /*public static Icon getEmojiIcon(int pos) {
    return getEmojiIcon(pos >> 16, pos & 0xFFFF);
  }*/
  public static Icon getEmojiIcon(long code) {
    Icon icon = emojiIcons.get(code);
    if (icon != null) {
      return icon;
    }
    
    Integer pos = emoji_pos.get(code);
    if (pos != null) {
      icon = getEmojiIcon(pos >> 16, pos & 0xFFFF);
      emojiIcons.put(code, icon);
      return icon;
    }
    
    return null;
  }
  
  public static class EmojiIcon implements Icon {
    public static final int ICON_SIZE = 20; 
    private Image sprite;
    private int col, row;
    private int spriteWidth, spriteHeight;

    public EmojiIcon(Image sprite, int spriteWidth, int spriteHeight, int col, int row) {
      this.sprite = sprite;
      this.spriteWidth = spriteWidth;
      this.spriteHeight = spriteHeight;
      this.col = col;
      this.row = row;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      g.drawImage(sprite, x, y, x + ICON_SIZE, y + ICON_SIZE, spriteWidth * col, spriteHeight * row, spriteWidth * (col + 1), spriteHeight * (row + 1), null);
    }

    public int getIconWidth() {
      return ICON_SIZE;
    }

    public int getIconHeight() {
      return ICON_SIZE;
    }
  }
  
  public static Image getImage(String name) {
    //return Toolkit.getDefaultToolkit().getImage(/*"NSImage://" +*/ name);
    try {
      //return ImageIO.read(new File(getFileName("res/" + name)));
      return ImageIO.read(Utils.class.getResourceAsStream("/" + name));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  public static String getFileName(String name) {
    if (new File(name).exists()) {
      return name;
    }
    
    if (System.getProperty("os.name").contains("Mac")) {
      try {
        String appBundle = (String) Class.forName("com.apple.eio.FileManager")
            .getMethod("getPathToApplicationBundle", (Class[]) null)
            .invoke(null, (Object[]) null);
        if (new File(appBundle + "/Contents/Resources/Java/" + name).exists()) {
          return appBundle + "/Contents/Resources/Java/" + name;
        }
      } catch (Exception e) {
        //fail quietly
      }
    }
    
    return "";
  }
  
  public static GridBagConstraints GBConstraints(int gridx, int gridy) {
    GridBagConstraints constr = new GridBagConstraints();
    constr.gridx = gridx;
    constr.gridy = gridy;
    return constr;
  }
  
  public static GridBagConstraints GBConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
    GridBagConstraints constr = new GridBagConstraints();
    constr.gridx = gridx;
    constr.gridy = gridy;
    constr.gridwidth = gridwidth;
    constr.gridheight = gridheight;
    return constr;
  }

  public static String num(int n, String[] cs, boolean append) {
    n = n % 100;
    if ((n % 10 == 0) || (n % 10 > 4) || (n > 4 && n < 21)) {
      return (append ? n : "") + cs[2];
    } else
    if (n % 10 == 1) {
      return (append ? n : "") + cs[0];
    } else {
      return (append ? n : "") + cs[1];
    }
  }

  public static int getChatOnline(DataService service, ChatFull chat) {
    int count = 0;
    if (chat.participants instanceof ChatParticipants) {
      for (TChatParticipant participant : ((ChatParticipants) chat.participants).participants) {
        TUser user = service.userManager.get(((ChatParticipant) participant).user_id);
        if (user.status instanceof UserStatusOnline) {
          count++;
        }
      }
    }
    return count;
  }

  public static TInputUser getInputUser(TUser user) {
    if (user instanceof UserForeign || user instanceof UserRequest) {
      return new InputUserForeign(user.id, user.access_hash);
    } else {
      return new InputUserContact(user.id);
    }
  }
}
