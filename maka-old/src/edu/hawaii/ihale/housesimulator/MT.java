/*
 * Mersenne Twister pseudo-random number generator functions.
 *
 * THE FUNCTIONS IN THIS FILE ARE FOR INTERNAL USE ONLY.  THEY'RE ALMOST
 * CERTAIN TO BE SUBJECT TO INCOMPATIBLE CHANGES OR DISAPPEAR COMPLETELY IN
 * FUTURE GNU MP RELEASES.
 *
 * Copyright 2002, 2003, 2006 Free Software Foundation, Inc.
 * This file is part of the GNU MP Library.
 *
 * The GNU MP Library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 *
 * The GNU MP Library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the GNU MP Library.  If not, see http://www.gnu.org/licenses/.
 */

package edu.hawaii.ihale.housesimulator;

import java.util.Date;

/**
 * This is a Java version of the GMP C-program "randmt.c" which implements the Mersenne Twister.
 * <code>MT</code> generates one pseudorandom unsigned integer (32bit) which is uniformly distributed
 * among 0 to 2^32-1 for each call. Before genrand() can be called, the MT(seed) or MT() constuctor
 * must be called once. <code>randinit_mt(seed)</code> set initial values to the working
 * area of 624 words with an initial seed. The <code>randinit_mt_noseed()</code> uses a default seed
 * of 5489 as recommended in "randmt.c" and will simply use the <code>default_state[]</code> as it
 * is; default initialization.
 * 
 * (For the applet's purposes, the seed is an odd 32-bit integer, but will generally be a modified
 * time stamp).
 * 
 * More information can be found at
 * <url>http://gmplib.org/<url>
 * <url>http://www.math.keio.ac.jp/matumoto/emt.html<url>
 * 
 * The GMP "randmt.c" implements the Mersenne Twister pseudorandom number generator by Takuji Nishimura
 * and Makoto Matsumoto. The buffer initialization function is different in order to permit seeds greater
 * than 2^32-1.
 * 
 * REFERENCE
 * Makoto Matsumoto and Takuji Nishimura, "Mersenne Twister: A 623-dimensionally
 * equidistributed uniform pseudorandom number generator", ACM Transactions on
 * Modelling and Computer Simulation, volume 8, January 1998, pp. 3-30. 
 * Available online http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/ARTICLES/mt.ps.gz (or .pdf)
 * 
 * @author Kurt Teichman
 * 
 */
public class MT {
	private static final int MATRIX_A = 0x9908b0df;   // private static final * constant vector a
	private static final int MASK_1 = 0x9d2c5680; // 2636928640
	private static final int MASK_2 = 0xefc60000; // 4022730752
	private static final int N = 624;
	private static final int M = 397;
	private static final int WARM_UP = 2000;
	//private static final int DEFAULT_SEED = 5489; 	// Just for documentation purposes
	private boolean haveSecondGaussian;
	private double secondGaussian;
	// the array for the state vector
	private int default_state[] = {0xD247B233,0x9E5AA8F1,0x0FFA981B,0x9DCB0980,0x74200F2B,0xA576D044,
			  			0xE9F05ADF,0x1538BFF5,0x59818BBF,0xCF9E58D8,0x09FCE032,0x6A1C663F,
			  			0x5116E78A,0x69B3E0FA,0x6D92D665,0xD0A8BE98,0xF669B734,0x41AC1B68,
			  			0x630423F1,0x4B8D6B8A,0xC2C46DD7,0x5680747D,0x43703E8F,0x3B6103D2,
			  			0x49E5EB3F,0xCBDAB4C1,0x9C988E23,0x747BEE0B,0x9111E329,0x9F031B5A,
			  			0xECCA71B9,0x2AFE4EF8,0x8421C7ED,0xAC89AFF1,0xAED90DF3,0x2DD74F01,
			  			0x14906A13,0x75873FA9,0xFF83F877,0x5028A0C9,0x11B4C41D,0x7CAEDBC4,
			  			0x8672D0A7,0x48A7C109,0x8320E59F,0xBC0B3D5F,0x75A30886,0xF9E0D128,
			  			0x41AF7580,0x239BB94D,0xC67A3C81,0x74EEBD6E,0xBC02B53C,0x727EA449,
			  			0x6B8A2806,0x5853B0DA,0xBDE032F4,0xCE234885,0x320D6145,0x48CC053F,
			  			0x00DBC4D2,0xD55A2397,0xE1059B6F,0x1C3E05D1,0x09657C64,0xD07CB661,
			  			0x6E982E34,0x6DD1D777,0xEDED1071,0xD79DFD65,0xF816DDCE,0xB6FAF1E4,
			  			0x1C771074,0x311835BD,0x18F952F7,0xF8F40350,0x4ECED354,0x7C8AC12B,
			  			0x31A9994D,0x4FD47747,0xDC227A23,0x6DFAFDDF,0x6796E748,0x0C6F634F,
			  			0xF992FA1D,0x4CF670C9,0x067DFD31,0xA7A3E1A5,0x8CD7D9DF,0x972CCB34,
			  			0x67C82156,0xD548F6A8,0x045CEC21,0xF3240BFB,0xDEF656A7,0x43DE08C5,
			  			0xDAD1F92F,0x3726C56B,0x1409F19A,0x942FD147,0xB926749C,0xADDC31B8,
			  			0x53D0D869,0xD1BA52FE,0x6722DF8C,0x22D95A74,0x7DC1B52A,0x1DEC6FD5,
			  			0x7262874D,0x0A725DC9,0xE6A8193D,0xA052835A,0xDC9AD928,0xE59EBB90,
			  			0x70DBA9FF,0xD612749D,0x5A5A638C,0x6086EC37,0x2A579709,0x1449EA3A,
			  			0xBC8E3C06,0x2F900666,0xFBE74FD1,0x6B35B911,0xF8335008,0xEF1E979D,
			  			0x738AB29D,0xA2DC0FDC,0x7696305D,0xF5429DAC,0x8C41813B,0x8073E02E,
			  			0xBEF83CCD,0x7B50A95A,0x05EE5862,0x00829ECE,0x8CA1958C,0xBE4EA2E2,
			  			0x4293BB73,0x656F7B23,0x417316D8,0x4467D7CF,0x2200E63B,0x109050C8,
			  			0x814CBE47,0x36B1D4A8,0x36AF9305,0x308327B3,0xEBCD7344,0xA738DE27,
			  			0x5A10C399,0x4142371D,0x64A18528,0x0B31E8B2,0x641057B9,0x6AFC363B,
			  			0x108AD953,0x9D4DA234,0x0C2D9159,0x1C8A1A1F,0x310C66BA,0x87AA1070,
			  			0xDAC832FF,0x0A433422,0x7AF15812,0x2D8D9BD0,0x995A25E9,0x25326CAC,
			  			0xA34384DB,0x4C8421CC,0x4F0315EC,0x29E8649E,0xA7732D6F,0x2E94D3E3,
			  			0x7D98A340,0x397C4D74,0x659DB4DE,0x747D4E9A,0xD9DB8435,0x4659DBE9,
			  			0x313E6DC5,0x29D104DC,0x9F226CBA,0x452F18B0,0xD0BC5068,0x844CA299,
			  			0x782B294E,0x4AE2EB7B,0xA4C475F8,0x70A81311,0x4B3E8BCC,0x7E20D4BA,
			  			0xABCA33C9,0x57BE2960,0x44F9B419,0x2E567746,0x72EB757A,0x102CC0E8,
			  			0xB07F32B9,0xD0DABD59,0xBA85AD6B,0xF3E20667,0x98D77D81,0x197AFA47,
			  			0x518EE9AC,0xE10CE5A2,0x01CF2C2A,0xD3A3AF3D,0x16DDFD65,0x669232F8,
			  			0x1C50A301,0xB93D9151,0x9354D3F4,0x847D79D0,0xD5FE2EC6,0x1F7B0610,
			  			0xFA6B90A5,0xC5879041,0x2E7DC05E,0x423F1F32,0xEF623DDB,0x49C13280,
			  			0x98714E92,0xC7B6E4AD,0xC4318466,0x0737F312,0x4D3C003F,0x9ACC1F1F,
			  			0x5F1C926D,0x085FA771,0x185A83A2,0xF9AA159D,0x0B0B0132,0xF98E7A43,
			  			0xCD9EBDBE,0x0190CB29,0x10D93FB6,0x3B8A4D97,0x66A65A41,0xE43E766F,
			  			0x77BE3C41,0xB9686364,0xCB36994D,0x6846A287,0x567E77F7,0x36178DD8,
			  			0xBDE6B1F2,0xB6EFDC64,0x82950324,0x42053F47,0xC09BE51C,0x0942D762,
			  			0x35F92C7F,0x367DEC61,0x6EE3D983,0xDBAAF78A,0x265D2C47,0x8EB4BF5C,
			  			0x33B232D7,0xB0137E77,0x373C39A7,0x8D2B2E76,0xC7510F01,0x50F9E032,
			  			0x7B1FDDDB,0x724C2AAE,0xB10ECB31,0xCCA3D1B8,0x7F0BCF10,0x4254BBBD,
			  			0xE3F93B97,0x2305039B,0x53120E22,0x1A2F3B9A,0x0FDDBD97,0x0118561E,
			  			0x0A798E13,0x9E0B3ACD,0xDB6C9F15,0xF512D0A2,0x9E8C3A28,0xEE2184AE,
			  			0x0051EC2F,0x2432F74F,0xB0AA66EA,0x55128D88,0xF7D83A38,0x4DAE8E82,
			  			0x3FDC98D6,0x5F0BD341,0x7244BE1D,0xC7B48E78,0x2D473053,0x43892E20,
			  			0xBA0F1F2A,0x524D4895,0x2E10BCB1,0x4C372D81,0x5C3E50CD,0xCF61CC2E,
			  			0x931709AB,0x81B3AEFC,0x39E9405E,0x7FFE108C,0x4FBB3FF8,0x06ABE450,
			  			0x7F5BF51E,0xA4E3CDFD,0xDB0F6C6F,0x159A1227,0x3B9FED55,0xD20B6F7F,
			  			0xFBE9CC83,0x64856619,0xBF52B8AF,0x9D7006B0,0x71165BC6,0xAE324AEE,
			  			0x29D27F2C,0x794C2086,0x74445CE2,0x782915CC,0xD4CE6886,0x3289AE7C,
			  			0x53DEF297,0x4185F7ED,0x88B72400,0x3C09DC11,0xBCE3AAB6,0x6A75934A,
			  			0xB267E399,0x000DF1BF,0x193BA5E2,0xFA3E1977,0x179E14F6,0x1EEDE298,
			  			0x691F0B06,0xB84F78AC,0xC1C15316,0xFFFF3AD6,0x0B457383,0x518CD612,
			  			0x05A00F3E,0xD5B7D275,0x4C5ECCD7,0xE02CD0BE,0x5558E9F2,0x0C89BBF0,
			  			0xA3D96227,0x2832D2B2,0xF667B897,0xD4556554,0xF9D2F01F,0xFA1E3FAE,
			  			0x52C2E1EE,0xE5451F31,0x7E849729,0xDABDB67A,0x54BF5E7E,0xF831C271,
			  			0x5F1A17E3,0x9D140AFE,0x92741C47,0x48CFABCE,0x9CBBE477,0x9C3EE57F,
			  			0xB07D4C39,0xCC21BCE2,0x697708B1,0x58DA2A6B,0x2370DB16,0x6E641948,
			  			0xACC5BD52,0x868F24CC,0xCA1DB0F5,0x4CADA492,0x3F443E54,0xC4A4D5E9,
			  			0xF00AD670,0xE93C86E0,0xFE90651A,0xDDE532A3,0xA66458DF,0xAB7D7151,
			  			0x0E2E775F,0xC9109F99,0x8D96D59F,0x73CEF14C,0xC74E88E9,0x02712DC0,
			  			0x04F41735,0x2E5914A2,0x59F4B2FB,0x0287FC83,0x80BC0343,0xF6B32559,
			  			0xC74178D4,0xF1D99123,0x383CCC07,0xACC0637D,0x0863A548,0xA6FCAC85,
			  			0x2A13EFF0,0xAF2EEDB1,0x41E72750,0xE0C6B342,0x5DA22B46,0x635559E0,
			  			0xD2EA40AC,0x10AA98C0,0x19096497,0x112C542B,0x2C85040C,0xA868E7D0,
			  			0x6E260188,0xF596D390,0xC3BB5D7A,0x7A2AA937,0xDFD15032,0x6780AE3B,
			  			0xDB5F9CD8,0x8BD266B0,0x7744AF12,0xB463B1B0,0x589629C9,0xE30DBC6E,
			  			0x880F5569,0x209E6E16,0x9DECA50C,0x02987A57,0xBED3EA57,0xD3A678AA,
			  			0x70DD030D,0x0CFD9C5D,0x92A18E99,0xF5740619,0x7F6F0A7D,0x134CAF9A,
			  			0x70F5BAE4,0x23DCA7B5,0x4D788FCD,0xC7F07847,0xBCF77DA1,0x9071D568,
			  			0xFC627EA1,0xAE004B77,0x66B54BCB,0x7EF2DAAC,0xDCD5AC30,0xB9BDF730,
			  			0x505A97A7,0x9D881FD3,0xADB796CC,0x94A1D202,0x97535D7F,0x31EC20C0,
			  			0xB1887A98,0xC1475069,0xA6F73AF3,0x71E4E067,0x46A569DE,0xD2ADE430,
			  			0x6F0762C7,0xF50876F4,0x53510542,0x03741C3E,0x53502224,0xD8E54D60,
			  			0x3C44AB1A,0x34972B46,0x74BFA89D,0xD7D768E0,0x37E605DC,0xE13D1BDF,
			  			0x5051C421,0xB9E057BE,0xB717A14C,0xA1730C43,0xB99638BE,0xB5D5F36D,
			  			0xE960D9EA,0x6B1388D3,0xECB6D3B6,0xBDBE8B83,0x2E29AFC5,0x764D71EC,
			  			0x4B8F4F43,0xC21DDC00,0xA63F657F,0x82678130,0xDBF535AC,0xA594FC58,
			  			0x942686BC,0xBD9B657B,0x4A0F9B61,0x44FF184F,0x38E10A2F,0x61910626,
			  			0x5E247636,0x7106D137,0xC62802F0,0xBD1D1F00,0x7CC0DCB2,0xED634909,
			  			0xDC13B24E,0x9799C499,0xD77E3D6A,0x14773B68,0x967A4FB7,0x35EECFB1,
			  			0x2A5110B8,0xE2F0AF94,0x9D09DEA5,0x20255D27,0x5771D34B,0xE1089EE4,
			  			0x246F330B,0x8F7CAEE5,0xD3064712,0x75CAFBEE,0xB94F7028,0xED953666,
			  			0x5D1975B4,0x5AF81271,0x13BE2025,0x85194659,0x30805331,0xEC9D46C0,
			  			0xBC027C36,0x2AF84188,0xC2141B80,0xC02B1E4A,0x04D36177,0xFC50E9D7,
			  			0x39CE79DA,0x917E0A00,0xEF7A0BF4,0xA98BD8D1,0x19424DD2,0x9439DF1F,
			  			0xC42AF746,0xADDBE83E,0x85221F0D,0x45563E90,0x9095EC52,0x77887B25,
			  			0x8AE46064,0xBD43B71A,0xBB541956,0x7366CF9D,0xEE8E1737,0xB5A727C9,
			  			0x5076B3E7,0xFC70BACA,0xCE135B75,0xC4E91AA3,0xF0341911,0x53430C3F,
			  			0x886B0824,0x6BB5B8B7,0x33E21254,0xF193B456,0x5B09617F,0x215FFF50,
			  			0x48D97EF1,0x356479AB,0x6EA9DDC4,0x0D352746,0xA2F5CE43,0xB226A1B3,
			  			0x1329EA3C,0x7A337CC2,0xB5CCE13D,0x563E3B5B,0x534E8E8F,0x561399C9,
			  			0xE1596392,0xB0F03125,0x4586645B,0x1F371847,0x94EAABD1,0x41F97EDD,
			  			0xE3E5A39B,0x71C774E2,0x507296F4,0x5960133B,0x7852C494,0x3F5B2691,
			  			0xA3F87774,0x5A7AF89E,0x17DA3F28,0xE9D9516D,0xFCC1C1D5,0xE4618628,
			  			0x04081047,0xD8E4DB5F,0xDC380416,0x8C4933E2,0x95074D53,0xB1B0032D,
			  			0xCC8102EA,0x71641243,0x98D6EB6A,0x90FEC945,0xA0914345,0x6FAB037D,
			  			0x70F49C4D,0x05BF5B0E,0x927AAF7F,0xA1940F61,0xFEE0756F,0xF815369F,
			  			0x5C00253B,0xF2B9762F,0x4AEB3CCC,0x1069F386,0xFBA4E7B9,0x70332665,
			  			0x6BCA810E,0x85AB8058,0xAE4B2B2F,0x9D120712,0xBEE8EACB,0x776A1112};
	private int mt[];
	private int mti; // Index of current value (ie: the pointer)
					 // mti==N+1 means mt[N] is not initialized, 
	private int mag01[];
	
	/**
	* This constructor calls <code>randinit_mt_noseed()</code> that uses the default_state[] for
	* random number generation.
	* 
	*/
	public MT() {
		randinit_mt_noseed();
	}

	/**
	* This constructor calls <code>randint_mt(seed)</code> using a given seed.
	*
	* @param seed generator starting number. Will be time of day.
	*/
	public MT(int seed) {
		randint_mt(seed);
	}

	/**
	* This method <code>randint_mt(seed)</code> initalizes the pseudo
	* random number generator with a magic equation with an initial seed.
	* This method does not use the <code>default_state[]</code>.
	*
	* @param seed from constructor
	*/

	private void randint_mt(int seed) {
		mt = new int[N];
		mt[0]= seed;   // replace initial value with seed\
		  							// in the default case, seed is already included in the initialization
									// of mt[].
		// use the seed with a function to set the rest of the values used in the generator
		for (mti = 1; mti < N; mti++) {
			mt[mti] = (69069 * mt[mti-1]);
		}
		// mag01[x] = x * MATRIX_A  for x=0,1
		mag01 = new int[2];
		mag01[0] = 0x0;
		mag01[1] = MATRIX_A;
	}
	
	/**
	* The method <code>randinit_mt_noseed()</code> uses a preinitialized
	* pseudorandom number generator seeded with the default 5489.
	*
	*/
	private void randinit_mt_noseed() {
		mt = new int[N];
		for (int i = 0; i < N; i++) {
		    mt[i] = default_state[i];
		}
		
		mti = WARM_UP % N;
		// mag01[x] = x * MATRIX_A  for x=0,1
		mag01 = new int[2];
		mag01[0] = 0x0;
		mag01[1] = MATRIX_A;
	}

	/**
	* The method <code>genrand()</code> generates the next random number.
	* @return y random number of the range [-2^32 - 1, 2^32 - 1]
	*/
	public int genrand() {
		int y;
		
		if (mti >= N) {
			int kk;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & 0x80000000) | (mt[kk+1] & 0x7FFFFFFF);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & 0x80000000) | (mt[kk+1] & 0x7FFFFFFF);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & 0x80000000) | (mt[0] & 0x7FFFFFFF);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			
			mti = 0; // reset pointer
		}
  
		// the twist?
		y = mt[mti ++];
		y ^= y >>> 11;
		y ^= (y << 7) & MASK_1;
		y ^= (y << 15) & MASK_2;
		y ^= (y >>> 18);

		return y; 
	}

	/**
	 * This method <code>nextDouble()</code> returns a random decimal of the range
	 * [0,1].
	 * 
	 * @return randomvalue of the range [0,1]
	 */
	public double nextDouble() {
		double y = Math.abs(genrand());
		double randomvalue = y / (Integer.MAX_VALUE);
		
		return randomvalue; 
	}
	
	/**
	 * The method <code>nextDouble(lower,upper)</code> returns a value with in the
	 * range [lower,upper].
	 * 
	 * @param lower bound
	 * @param upper bound
	 * @return randomvalue bounded by [lower,upper].
	 */
	public double nextDouble(double lower, double upper) {
		double y = Math.abs(genrand());
		double ratio = y / (Integer.MAX_VALUE);
		double randomvalue;
		
		randomvalue = (ratio) * (upper - lower) + lower;
		return randomvalue;
	}
	
	/**
	 * The method <code>nextGaussian(mean,variance)</code> allows for the creation of
	 * a normally distributed random number within the bound [0,1]. It should be noted that if
	 * the normalized random number generated is outside the bounds [0,1], the method will 
	 * recursively call itself to generate another normalized random number. This method was
	 * inspired by <url>http://download.oracle.com/javase/1.4.2/docs/api/java/
	 * util/Random.html#nextGaussian()<url>.
	 *   
	 * @param mean mean value.
	 * @param variance variance value.
	 * @return randomnumber within the bound [0,1].
	 */
	public double nextGaussian(double mean, double variance) {
		if (haveSecondGaussian) {
            haveSecondGaussian = false;
            return (validGaussian(secondGaussian,0,1) ? 
                secondGaussian : nextGaussian(mean,variance));
		}
		else {
			double v1, v2, s;
			do { 
				v1 = 2.0 * nextDouble() - 1.0;   // between -1.0 and 1.0
				v2 = 2.0 * nextDouble() - 1.0;   // between -1.0 and 1.0
				s = v1 * v1 + v2 * v2;
			} while (s >= 1.0 || s == 0.0);
			
			double multiplier = Math.sqrt( -2.0 * Math.log(s) / s);
			secondGaussian = mean + v2 * multiplier * Math.sqrt(variance);
			haveSecondGaussian = true;
			double firstGaussian = mean + v1 * multiplier * Math.sqrt(variance);
			return (validGaussian(firstGaussian,0,1) ? 
			    firstGaussian : nextGaussian(mean,variance));
		}
	}
	
	/**
	 * The method <code>validGaussian(gaussian,lower,upper)</code> checks to see if the
	 * random gaussian number generated is within the range [lower,upper].
	 * 
	 * @param gaussian gaussian value.
	 * @param lower lower value.
	 * @param upper upper value.
	 * @return boolean indicating whether the gaussian number is lower <= number <= upper.
	 */
	public boolean validGaussian(double gaussian, int lower, int upper) {
		return (gaussian <= upper && gaussian >= lower);
	}
	
	/**
	 * The method <code>testNormalDistribution(mt)</code>
	 * generates 100000 random numbers in a gaussian and counts their
	 * frequencies in an array of "bins". This array is printed to
	 * the console in csv format.
	 * 
	 * @param mt Mersenne Twister object
	 */
	public void testNormalDistribution(MT mt) {
		double x;
		double[] testArray = new double[100];
		for (int i = 0; i < 100000; i ++) {
			x = mt.nextGaussian(.5, .05) * 100;
			testArray[(int)(Math.floor(x))] ++;
		}
		for (int z = 0; z < testArray.length; z ++) {
			System.out.println(z + ',' + testArray[z]);
		}
	}
	
	/**
	 * The method <code>testUniformDistribution(mt)</code>
	 * uniformly generates 100000 random numbers and counts their
	 * frequencies in an array of "bins". This array is printed to
	 * the console in csv format.
	 * 
	 * @param mt Mersenne Twister object
	 */
	public void testUniformDistribution(MT mt) {
		double x;
		double[] testArray = new double[100];
		for (int i = 0; i < 100000; i ++) {
			x = Math.floor(mt.nextDouble(0, 100));
			testArray[(int)(x)] ++;
		}
		
		for (int z = 0; z < testArray.length; z ++) {
			System.out.println(z + ',' + testArray[z]);
		}
	}
	
	/**
	* This method <code>main(args[])</code> simply is a sandbox to test some of the 
	* functions in this class.
	* 
	* @param args not currently used
	*/
	public static void main(String args[]) { 
		Date d = new Date();
		MT r = new MT((int)d.getTime());

		r.testUniformDistribution(r);
	}

}