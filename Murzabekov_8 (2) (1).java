class Prog
{

    public double s = 0;
    public double avg = 0;
    public double os = 0;

    //@ requires nums != null;
    //@ requires nums.length > 0 && nums.length <= 100;
    //@ requires \forall int i; 0 <= i && i < nums.length; nums[i] >= 10 && nums[i] < 100;
    //@ requires s==0;
    //@ requires os==0;
    //@ requires avg ==0;
    //@ ensures \result*nums.length == s;
    //@ ensures 0<= \result;
    //@ ensures avg == \result;
    public double findAvg (int[] nums)
    {
        int i = 0;

        //@ loop_invariant 0 <= i && i <= nums.length;
        //@ loop_invariant 0 <= os;
        //@ loop_invariant 0 <= s;
        //@ loop_invariant 0 <= avg;
        while (i < nums.length)
        {
            //@ assert avg>=0;
            //@ assert s>=0;
            //@ assert os>=0;
            //@ assert 0 <= i && i <= nums.length;

            os = s;
            //@ assert s-os ==0;

            s += nums[i];
            avg = s / (i+1);

            //@ assert 0 <= i && i <= nums.length;
            //@ assert s == os + nums[i];
            //@ assert nums[i] >=10 && nums[i] < 1000;
            //@ assert (s >= avg*(i+1))&& (s-(i+1) * avg)<= 0.001f || ((i+1) * avg - s)<= 0.001f && s < avg*(i+1);

            //@ assert avg>=0;
            //@ assert s>=0;
            //@ assert os>=0;
            //@ assert s-os >=0;
            //@ assert s>=nums[i];
            ++i;
        }

        avg = s / nums.length;

        return avg;
    }
}