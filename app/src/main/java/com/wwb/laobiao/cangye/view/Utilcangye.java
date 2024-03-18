package com.wwb.laobiao.cangye.view;

import com.yangna.lbdsp.R;

public class Utilcangye {
    public static int getxingid(int idex) {
        int idv= R.id.imageView01;
        switch (idex)
	        {
				case 0:
				return R.id.imageView01;
				case 1:
				return R.id.imageView02;
				case 2:
				return R.id.imageView03;
				case 3:
				return R.id.imageView04;
				case 4:
				return R.id.imageView05;
				case 5:
				return R.id.imageView11;
				case 6:
				return R.id.imageView12;
				case 7:
				return R.id.imageView13;
				case 8:
				return R.id.imageView14;
				case 9:
				return R.id.imageView15;
				case 10:
				return R.id.imageView21;
				case 11:
				return R.id.imageView22;
				case 12:
				return R.id.imageView23;
				case 13:
				return R.id.imageView24;
				case 14:
				return R.id.imageView25;
				case 15:
				return R.id.imageView31;
				case 16:
				return R.id.imageView32;
				case 17:
				return R.id.imageView33;
				case 18:
				return R.id.imageView34;
				case 19:
				return R.id.imageView35;
	        }
        return idv;
    }

    public static int getidex(int id) {
        int idv= R.id.imageView01;
        switch (id)
	        {
				case R.id.imageView01:
				return 0;
				case R.id.imageView02:
				return 1;
				case R.id.imageView03:
				return 2;
				case R.id.imageView04:
				return 3;
				case R.id.imageView05:
				return 4;
				case R.id.imageView11:
				return 5;
				case R.id.imageView12:
				return 6;
				case R.id.imageView13:
				return 7;
				case R.id.imageView14:
				return 8;
				case R.id.imageView15:
				return 9;
				case R.id.imageView21:
				return 10;
				case R.id.imageView22:
				return 11;
				case R.id.imageView23:
				return 12;
				case R.id.imageView24:
				return 13;
				case R.id.imageView25:
				return 14;
				case R.id.imageView31:
				return 15;
				case R.id.imageView32:
				return 16;
				case R.id.imageView33:
				return 17;
				case R.id.imageView34:
				return 18;
				case R.id.imageView35:
				return 19;
	        }
        return -1;
    }
}
