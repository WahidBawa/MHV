using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DataFetcher : MonoBehaviour
{
    Vector3 middle, palm, pinky, thumb;
    public double pitch, roll;
    // Start is called before the first frame update
    void Start()
    {

    }
 
    // Update is called once per frame
    void Update()
    {
        middle = this.transform.Find("middle").Find("bone3").position;
        palm = this.transform.Find("palm").position;
        pinky = this.transform.Find("pinky").Find("bone3").position;
        thumb = this.transform.Find("thumb").Find("bone3").position;
        pitch = (middle.y - palm.y) / (middle.z - palm.z);
        roll = (pinky.y - thumb.y) / (pinky.x - thumb.x);

        Debug.Log("PITCH: " + pitch + " ROLL:" + roll);

        //Debug.Log(middle.x * 100);
        //foreach (Transform child in this.transform)
        //{
        //}


    }
}
