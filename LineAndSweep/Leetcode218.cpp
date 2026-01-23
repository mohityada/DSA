class Solution {
public:
    vector<vector<int>> getSkyline(vector<vector<int>>& buildings) {
        vector<vector<int>> events;
        int n = buildings.size(), i = 0;
        while(i < n){
            events.push_back({buildings[i][0], -buildings[i][2]});
            events.push_back({buildings[i][1], buildings[i][2]});
            i++;
        }

        sort(events.begin(), events.end(), [](auto const &e1, auto const &e2){
            return (e1[0] != e2[0]) ? e1[0] < e2[0] : e1[1] < e2[1];
        });

        for(i = 0; i < 2*n; i++){
            cout << events[i][0] << " " << events[i][1] << "\n";
        }

        i = 0;

        int active = 0, point, height;

        priority_queue<int> maxPQ;
        vector<vector<int>> finalAnswer;
        unordered_map<int, int> isHeightActive;
        while(i < 2*n){
            point = events[i][0];
            height = events[i][1];
            
            if(height < 0){
                active++;
                maxPQ.push(-height);
                isHeightActive[-height]++;
                if(finalAnswer.empty() || (finalAnswer.back()[1] < -height)){
                    finalAnswer.push_back({point, -height});
                }

            } else{
                active--;
                isHeightActive[height]--;
                /** 
                *   end of some building height and our latest height added may require new addition 
                *   --> isHeightActive[height] == 0 means it tells no building with "height" exisits
                */ 
                if(finalAnswer.back()[1] == height && isHeightActive[height] == 0){
                    while (!maxPQ.empty()){
                        int maxPossibleHeight = maxPQ.top();
                        //if max possible height = "height" then no need add as it will split the same height building
                        if(isHeightActive[ht] > 0 && maxPossibleHeight != height){ 
                            finalAnswer.push_back({point, ht});
                            break;
                        }
                        maxPQ.pop();
                    }
                }
            }
            if(active == 0){
                finalAnswer.push_back({point, 0});
            }
            i++;
        }
        
        return finalAnswer;
    }
};