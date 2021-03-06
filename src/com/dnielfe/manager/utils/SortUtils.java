/*
 * Copyright (C) 2014 Simple Explorer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package com.dnielfe.manager.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import com.dnielfe.manager.Browser;
import com.dnielfe.manager.settings.Settings;

public class SortUtils {

	private static final int SORT_ALPHA = 0;
	private static final int SORT_TYPE = 1;
	private static final int SORT_SIZE = 2;

	public static ArrayList<String> sortList(ArrayList<String> content,
			String current) {

		switch (Settings.mSortType) {
		case SORT_ALPHA:
			int len2 = content != null ? content.size() : 0;
			String[] alph_ar;

			alph_ar = new String[len2];

			content.toArray(alph_ar);
			Arrays.sort(alph_ar, Comparator_ALPH);
			content.clear();

			for (String a : alph_ar) {
				content.add(a);
			}
			break;
		case SORT_SIZE:
			int len = content != null ? content.size() : 0;
			int index = 0;
			String[] size_ar;

			size_ar = new String[len];
			content.toArray(size_ar);
			Arrays.sort(size_ar, Comparator_SIZE);
			content.clear();

			for (String a : size_ar) {
				if (new File(current + "/" + a).isDirectory())
					content.add(index++, a);
				else
					content.add((String) a);
			}
			break;
		case SORT_TYPE:
			int len1 = content != null ? content.size() : 0;
			int dirindex = 0;
			String[] items1;

			items1 = new String[len1];
			content.toArray(items1);
			Arrays.sort(items1, Comparator_TYPE);
			content.clear();

			for (String a : items1) {
				if (new File(current + "/" + a).isDirectory())
					content.add(dirindex++, a);
				else
					content.add(a);
			}
			break;
		}

		return content;
	}

	public static final Comparator<? super String> Comparator_ALPH = new Comparator<String>() {

		@Override
		public int compare(String arg0, String arg1) {
			return arg0.toLowerCase().compareTo(arg1.toLowerCase());
		}
	};

	public final static Comparator<? super String> Comparator_SIZE = new Comparator<String>() {

		@Override
		public int compare(String arg0, String arg1) {
			String dir = Browser.mCurrentPath;
			Long first = new File(dir + "/" + arg0).length();
			Long second = new File(dir + "/" + arg1).length();

			return first.compareTo(second);
		}
	};

	public final static Comparator<? super String> Comparator_TYPE = new Comparator<String>() {

		@Override
		public int compare(String arg0, String arg1) {
			String ext = null;
			String ext2 = null;
			int ret;

			try {
				ext = arg0.substring(arg0.lastIndexOf(".") + 1, arg0.length())
						.toLowerCase();
				ext2 = arg1.substring(arg1.lastIndexOf(".") + 1, arg1.length())
						.toLowerCase();

			} catch (IndexOutOfBoundsException e) {
				return 0;
			}
			ret = ext.compareTo(ext2);

			if (ret == 0)
				return arg0.toLowerCase().compareTo(arg1.toLowerCase());

			return ret;
		}
	};
}